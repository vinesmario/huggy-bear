package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.common.util.SpringContextUtil;
import com.vinesmario.microservice.client.storage.dto.StorageTemplateDTO;
import com.vinesmario.microservice.server.storage.service.StorageTemplateService;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategy;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategyFactory;
import com.vinesmario.microservice.server.storage.web.rest.anonymous.StorageResource;
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

public class StorageTemplateFactory extends AbstractStorageFactory<StorageTemplateDTO> {

    @Override
    public StorageTemplateDTO create(MultipartFile multipartFile, Long tenantId) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = uuid + "." + extension;
        String fileRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + fileName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            fileRelativePath = tenantId + "/" + fileRelativePath;
        }

        StorageTemplateDTO storageTemplateDTO = new StorageTemplateDTO();
        storageTemplateDTO.setTenantId(tenantId);
        storageTemplateDTO.setUuid(uuid);
        storageTemplateDTO.setFileExtension(extension);
        storageTemplateDTO.setFileName(fileName);
        storageTemplateDTO.setFileSize(multipartFile.getSize());
        // 文件MD5、SHA1
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        storageTemplateDTO.setFileMd5Hex(md5Hex);
        storageTemplateDTO.setFileSha1Hex(sha1Hex);

        // 选择文件上传策略
        StorageStrategy storageStrategy = StorageStrategyFactory.build();
        storageStrategy.upload(multipartFile, fileRelativePath, storageTemplateDTO);

        // 文件访问绝对url为空，补充文件访问相对url
        if (StringUtils.isBlank(storageTemplateDTO.getFileAbsoluteUrl())) {
            String url = StorageResource.class.getAnnotation(RequestMapping.class).value()[0];
            url += StorageResource.class.getMethod("downloadTemplate", String.class).getAnnotation(GetMapping.class).value()[0];
            storageTemplateDTO.setFileRelativeUrl(url.replace("{uuid}", storageTemplateDTO.getUuid()));
        }
        // 文件记录持久化
        if (storageStrategy.isPersistent()) {
            StorageTemplateService service = SpringContextUtil.getBean(StorageTemplateService.class);
            service.create(storageTemplateDTO);
        }

        storageTemplateDTO.setFileAbsolutePath(null);
        return storageTemplateDTO;
    }

}
