package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.storage.dto.StoragePdfDTO;
import com.vinesmario.microservice.server.common.util.SpringContextUtil;
import com.vinesmario.microservice.server.storage.web.rest.anonymous.StorageResource;
import com.vinesmario.microservice.server.storage.service.StoragePdfService;
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

public class StoragePdfFactory extends AbstractStorageFactory<StoragePdfDTO> {

    @Override
    public StoragePdfDTO create(MultipartFile multipartFile, Long tenantId) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = uuid + "." + extension;
        String fileRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + fileName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            fileRelativePath = tenantId + "/" + fileRelativePath;
        }

        StoragePdfDTO storagePdfDTO = new StoragePdfDTO();
        storagePdfDTO.setTenantId(tenantId);
        storagePdfDTO.setUuid(uuid);
        storagePdfDTO.setFileExtension(extension);
        storagePdfDTO.setFileName(fileName);
        storagePdfDTO.setFileSize(multipartFile.getSize());
        // 文件MD5、SHA1
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        storagePdfDTO.setFileMd5Hex(md5Hex);
        storagePdfDTO.setFileSha1Hex(sha1Hex);

        // 选择文件上传策略
        StorageStrategy storageStrategy = StorageStrategyFactory.build();
        storageStrategy.upload(multipartFile, fileRelativePath, storagePdfDTO);

        // 文件访问绝对url为空，补充文件访问相对url
        if (StringUtils.isBlank(storagePdfDTO.getFileAbsoluteUrl())) {
            String url = StorageResource.class.getAnnotation(RequestMapping.class).value()[0];
            url += StorageResource.class.getMethod("downloadPdf", String.class).getAnnotation(GetMapping.class).value()[0];
            storagePdfDTO.setFileRelativeUrl(url.replace("{uuid}", storagePdfDTO.getUuid()));
        }
        // 文件记录持久化
        if (storageStrategy.isPersistent()) {
            StoragePdfService service = SpringContextUtil.getBean(StoragePdfService.class);
            service.create(storagePdfDTO);
        }

        storagePdfDTO.setFileAbsolutePath(null);
        return storagePdfDTO;
    }

}
