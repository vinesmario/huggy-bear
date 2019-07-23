package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.server.common.util.SpringContextUtil;
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

public class StorageFileFactory extends AbstractStorageFactory<StorageFileDto> {

    @Override
    public StorageFileDto create(MultipartFile multipartFile, Long tenantId) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = uuid + "." + extension;
        String fileRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + fileName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            fileRelativePath = tenantId + "/" + fileRelativePath;
        }

        StorageFileDto storageFileDto = new StorageFileDto();
        storageFileDto.setTenantId(tenantId);
        storageFileDto.setUuid(uuid);
        storageFileDto.setFileExtension(extension);
        storageFileDto.setFileName(fileName);
        storageFileDto.setFileSize(multipartFile.getSize());
        // 文件MD5、SHA1
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        storageFileDto.setFileMd5Hex(md5Hex);
        storageFileDto.setFileSha1Hex(sha1Hex);

        // 选择文件上传策略
        StorageStrategy storageStrategy = StorageStrategyFactory.build();
        storageStrategy.upload(multipartFile, fileRelativePath, storageFileDto);

        // 文件访问绝对url为空，补充文件访问相对url
        if (StringUtils.isBlank(storageFileDto.getFileAbsoluteUrl())) {
            String url = StorageFileResource.class.getAnnotation(RequestMapping.class).value()[0];
            url += StorageFileResource.class.getMethod("download", String.class).getAnnotation(GetMapping.class).value()[0];
            storageFileDto.setFileRelativeUrl(url.replace("{uuid}", storageFileDto.getUuid()));
//            storageFileDto.setFileRelativeUrl("/api/v1/storage_file/download/{uuid}".replace("{uuid}", storageFileDto.getUuid()));
        }
        // 文件记录持久化
        if (storageStrategy.isPersistent()) {
            StorageFileService service = SpringContextUtil.getBean(StorageFileService.class);
            service.create(storageFileDto);
        }

        storageFileDto.setFileAbsolutePath(null);
        return storageFileDto;
    }

}
