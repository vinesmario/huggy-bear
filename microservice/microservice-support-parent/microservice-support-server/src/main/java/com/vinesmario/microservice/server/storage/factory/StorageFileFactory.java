package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.storage.dto.StorageFileDTO;
import com.vinesmario.microservice.server.common.util.SpringContextUtil;
import com.vinesmario.microservice.server.ignore.StorageResource;
import com.vinesmario.microservice.server.storage.service.StorageFileService;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategy;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategyFactory;
import com.vinesmario.microservice.server.storage.web.rest.v1.StorageFileResource;
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

public class StorageFileFactory extends AbstractStorageFactory<StorageFileDTO> {

    @Override
    public StorageFileDTO create(MultipartFile multipartFile, Long tenantId) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = uuid + "." + extension;
        String fileRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + fileName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            fileRelativePath = tenantId + "/" + fileRelativePath;
        }

        StorageFileDTO storageFileDTO = new StorageFileDTO();
        storageFileDTO.setTenantId(tenantId);
        storageFileDTO.setUuid(uuid);
        storageFileDTO.setFileExtension(extension);
        storageFileDTO.setFileName(fileName);
        storageFileDTO.setFileSize(multipartFile.getSize());
        // 文件MD5、SHA1
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        storageFileDTO.setFileMd5Hex(md5Hex);
        storageFileDTO.setFileSha1Hex(sha1Hex);

        // 选择文件上传策略
        StorageStrategy storageStrategy = StorageStrategyFactory.build();
        storageStrategy.upload(multipartFile, fileRelativePath, storageFileDTO);

        // 文件访问绝对url为空，补充文件访问相对url
        if (StringUtils.isBlank(storageFileDTO.getFileAbsoluteUrl())) {
            String url = StorageResource.class.getAnnotation(RequestMapping.class).value()[0];
            url += StorageResource.class.getMethod("downloadFile", String.class).getAnnotation(GetMapping.class).value()[0];
            storageFileDTO.setFileRelativeUrl(url.replace("{uuid}", storageFileDTO.getUuid()));
        }
        // 文件记录持久化
        if (storageStrategy.isPersistent()) {
            StorageFileService service = SpringContextUtil.getBean(StorageFileService.class);
            service.create(storageFileDTO);
        }

        storageFileDTO.setFileAbsolutePath(null);
        return storageFileDTO;
    }

}
