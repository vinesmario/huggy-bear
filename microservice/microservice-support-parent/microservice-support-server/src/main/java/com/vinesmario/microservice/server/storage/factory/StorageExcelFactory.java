package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.storage.dto.StorageExcelDto;
import com.vinesmario.microservice.server.common.util.SpringContextUtil;
import com.vinesmario.microservice.server.storage.service.StorageExcelService;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategy;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategyFactory;
import com.vinesmario.microservice.server.storage.web.rest.v1.StorageExcelResource;
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

public class StorageExcelFactory extends AbstractStorageFactory<StorageExcelDto> {

    @Override
    public StorageExcelDto create(MultipartFile multipartFile, Long tenantId) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = uuid + "." + extension;
        String fileRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + fileName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            fileRelativePath = tenantId + "/" + fileRelativePath;
        }

        StorageExcelDto storageExcelDto = new StorageExcelDto();
        storageExcelDto.setTenantId(tenantId);
        storageExcelDto.setUuid(uuid);
        storageExcelDto.setFileExtension(extension);
        storageExcelDto.setFileName(fileName);
        storageExcelDto.setFileSize(multipartFile.getSize());
        // 文件MD5、SHA1
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        storageExcelDto.setFileMd5Hex(md5Hex);
        storageExcelDto.setFileSha1Hex(sha1Hex);

        // 选择文件上传策略
        StorageStrategy storageStrategy = StorageStrategyFactory.build();
        storageStrategy.upload(multipartFile, fileRelativePath, storageExcelDto);

        // 文件访问绝对url为空，补充文件访问相对url
        if (StringUtils.isBlank(storageExcelDto.getFileAbsoluteUrl())) {
            String url = StorageExcelResource.class.getAnnotation(RequestMapping.class).value()[0];
            url += StorageExcelResource.class.getMethod("download", String.class).getAnnotation(GetMapping.class).value()[0];
            storageExcelDto.setFileRelativeUrl(url.replace("{uuid}", storageExcelDto.getUuid()));
//            storageExcelDto.setFileRelativeUrl("/api/v1/storage_excel/download/{uuid}".replace("{uuid}", storageExcelDto.getUuid()));
        }

        // 文件记录持久化
        if (storageStrategy.isPersistent()) {
            StorageExcelService service = SpringContextUtil.getBean(StorageExcelService.class);
            service.create(storageExcelDto);
        }

        storageExcelDto.setFileAbsolutePath(null);
        return storageExcelDto;
    }

}
