package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDTO;
import com.vinesmario.microservice.server.common.util.SpringContextUtil;
import com.vinesmario.microservice.server.storage.web.rest.anonymous.StorageResource;
import com.vinesmario.microservice.server.storage.service.StoragePdfSplitedService;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategy;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategyFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class StoragePdfSplitedFactory extends AbstractStorageFactory<StoragePdfSplitedDTO> {

    @Override
    public StoragePdfSplitedDTO create(MultipartFile multipartFile, Long tenantId) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = uuid + "." + extension;
        String fileRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + fileName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            fileRelativePath = tenantId + "/" + fileRelativePath;
        }

        StoragePdfSplitedDTO storagePdfSplitedDTO = new StoragePdfSplitedDTO();
        storagePdfSplitedDTO.setTenantId(tenantId);
        storagePdfSplitedDTO.setUuid(uuid);
        storagePdfSplitedDTO.setFileExtension(extension);
        storagePdfSplitedDTO.setFileName(fileName);
        storagePdfSplitedDTO.setFileSize(multipartFile.getSize());
        // 文件MD5、SHA1
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        storagePdfSplitedDTO.setFileMd5Hex(md5Hex);
        storagePdfSplitedDTO.setFileSha1Hex(sha1Hex);

        // 选择文件上传策略
        StorageStrategy storageStrategy = StorageStrategyFactory.build();
        storageStrategy.upload(multipartFile, fileRelativePath, storagePdfSplitedDTO);

        // 文件访问绝对url为空，补充文件访问相对url
        if (StringUtils.isBlank(storagePdfSplitedDTO.getFileAbsoluteUrl())) {
            String url = StorageResource.class.getAnnotation(RequestMapping.class).value()[0];
            url += StorageResource.class.getMethod("downloadPdfSplited", String.class).getAnnotation(GetMapping.class).value()[0];
            storagePdfSplitedDTO.setFileRelativeUrl(url.replace("{uuid}", storagePdfSplitedDTO.getUuid()));
        }
        // 文件记录持久化
        if (storageStrategy.isPersistent()) {
            StoragePdfSplitedService service = SpringContextUtil.getBean(StoragePdfSplitedService.class);
            service.create(storagePdfSplitedDTO);
        }

        storagePdfSplitedDTO.setFileAbsolutePath(null);
        return storagePdfSplitedDTO;
    }

}
