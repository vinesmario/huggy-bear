package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.storage.dto.StoragePdfDto;
import com.vinesmario.microservice.server.common.util.SpringContextUtil;
import com.vinesmario.microservice.server.storage.service.StoragePdfService;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategy;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategyFactory;
import com.vinesmario.microservice.server.storage.web.rest.v1.StoragePdfResource;
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

public class StoragePdfFactory extends AbstractStorageFactory<StoragePdfDto> {

    @Override
    public StoragePdfDto create(MultipartFile multipartFile, Long tenantId) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = uuid + "." + extension;
        String fileRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + fileName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            fileRelativePath = tenantId + "/" + fileRelativePath;
        }

        StoragePdfDto storagePdfDto = new StoragePdfDto();
        storagePdfDto.setTenantId(tenantId);
        storagePdfDto.setUuid(uuid);
        storagePdfDto.setFileExtension(extension);
        storagePdfDto.setFileName(fileName);
        storagePdfDto.setFileSize(multipartFile.getSize());
        // 文件MD5、SHA1
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        storagePdfDto.setFileMd5Hex(md5Hex);
        storagePdfDto.setFileSha1Hex(sha1Hex);

        // 选择文件上传策略
        StorageStrategy storageStrategy = StorageStrategyFactory.build();
        storageStrategy.upload(multipartFile, fileRelativePath, storagePdfDto);

        // 文件访问绝对url为空，补充文件访问相对url
        if (StringUtils.isBlank(storagePdfDto.getFileAbsoluteUrl())) {
            String url = StoragePdfResource.class.getAnnotation(RequestMapping.class).value()[0];
            url += StoragePdfResource.class.getMethod("download", String.class).getAnnotation(GetMapping.class).value()[0];
            storagePdfDto.setFileRelativeUrl(url.replace("{uuid}", storagePdfDto.getUuid()));
//            storagePdfDto.setFileRelativeUrl("/api/v1/storage_pdf/download/{uuid}".replace("{uuid}", storagePdfDto.getUuid()));
        }
        // 文件记录持久化
        if (storageStrategy.isPersistent()) {
            StoragePdfService service = SpringContextUtil.getBean(StoragePdfService.class);
            service.create(storagePdfDto);
        }

        storagePdfDto.setFileAbsolutePath(null);
        return storagePdfDto;
    }

}
