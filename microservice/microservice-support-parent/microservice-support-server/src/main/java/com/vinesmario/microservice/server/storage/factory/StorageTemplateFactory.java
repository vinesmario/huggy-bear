package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.storage.dto.StorageTemplateDto;
import com.vinesmario.microservice.server.common.util.SpringContextUtil;
import com.vinesmario.microservice.server.storage.service.StorageTemplateService;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategy;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategyFactory;
import com.vinesmario.microservice.server.storage.web.rest.v1.StorageTemplateResource;
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

public class StorageTemplateFactory extends AbstractStorageFactory<StorageTemplateDto> {

    @Override
    public StorageTemplateDto create(MultipartFile multipartFile, Long tenantId) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = uuid + "." + extension;
        String fileRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + fileName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            fileRelativePath = tenantId + "/" + fileRelativePath;
        }

        StorageTemplateDto storageTemplateDto = new StorageTemplateDto();
        storageTemplateDto.setTenantId(tenantId);
        storageTemplateDto.setUuid(uuid);
        storageTemplateDto.setFileExtension(extension);
        storageTemplateDto.setFileName(fileName);
        storageTemplateDto.setFileSize(multipartFile.getSize());
        // 文件MD5、SHA1
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        storageTemplateDto.setFileMd5Hex(md5Hex);
        storageTemplateDto.setFileSha1Hex(sha1Hex);

        // 选择文件上传策略
        StorageStrategy storageStrategy = StorageStrategyFactory.build();
        storageStrategy.upload(multipartFile, fileRelativePath, storageTemplateDto);

        // 文件访问绝对url为空，补充文件访问相对url
        if (StringUtils.isBlank(storageTemplateDto.getFileAbsoluteUrl())) {
            String url = StorageTemplateResource.class.getAnnotation(RequestMapping.class).value()[0];
            url += StorageTemplateResource.class.getMethod("download", String.class).getAnnotation(GetMapping.class).value()[0];
            storageTemplateDto.setFileRelativeUrl(url.replace("{uuid}", storageTemplateDto.getUuid()));
//            storageTemplateDto.setFileRelativeUrl("/api/v1/storage_template/download/{uuid}".replace("{uuid}", storageTemplateDto.getUuid()));
        }
        // 文件记录持久化
        if (storageStrategy.isPersistent()) {
            StorageTemplateService service = SpringContextUtil.getBean(StorageTemplateService.class);
            service.create(storageTemplateDto);
        }

        storageTemplateDto.setFileAbsolutePath(null);
        return storageTemplateDto;
    }

}
