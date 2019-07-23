package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDto;
import com.vinesmario.microservice.server.common.util.SpringContextUtil;
import com.vinesmario.microservice.server.storage.service.StoragePdfSplitedService;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategy;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategyFactory;
import com.vinesmario.microservice.server.storage.web.rest.v1.StoragePdfSplitedResource;
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

public class StoragePdfSplitedFactory extends AbstractStorageFactory<StoragePdfSplitedDto> {

    @Override
    public StoragePdfSplitedDto create(MultipartFile multipartFile, Long tenantId) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = uuid + "." + extension;
        String fileRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + fileName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            fileRelativePath = tenantId + "/" + fileRelativePath;
        }

        StoragePdfSplitedDto storagePdfSplitedDto = new StoragePdfSplitedDto();
        storagePdfSplitedDto.setTenantId(tenantId);
        storagePdfSplitedDto.setUuid(uuid);
        storagePdfSplitedDto.setFileExtension(extension);
        storagePdfSplitedDto.setFileName(fileName);
        storagePdfSplitedDto.setFileSize(multipartFile.getSize());
        // 文件MD5、SHA1
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        storagePdfSplitedDto.setFileMd5Hex(md5Hex);
        storagePdfSplitedDto.setFileSha1Hex(sha1Hex);

        // 选择文件上传策略
        StorageStrategy storageStrategy = StorageStrategyFactory.build();
        storageStrategy.upload(multipartFile, fileRelativePath, storagePdfSplitedDto);

        // 文件访问绝对url为空，补充文件访问相对url
        if (StringUtils.isBlank(storagePdfSplitedDto.getFileAbsoluteUrl())) {
            String url = StoragePdfSplitedResource.class.getAnnotation(RequestMapping.class).value()[0];
            url += StoragePdfSplitedResource.class.getMethod("download", String.class).getAnnotation(GetMapping.class).value()[0];
            storagePdfSplitedDto.setFileRelativeUrl(url.replace("{uuid}", storagePdfSplitedDto.getUuid()));
//            storagePdfSplitedDto.setFileRelativeUrl("/api/v1/storage_pdf_splited/download/{uuid}".replace("{uuid}", storagePdfSplitedDto.getUuid()));
        }
        // 文件记录持久化
        if (storageStrategy.isPersistent()) {
            StoragePdfSplitedService service = SpringContextUtil.getBean(StoragePdfSplitedService.class);
            service.create(storagePdfSplitedDto);
        }

        storagePdfSplitedDto.setFileAbsolutePath(null);
        return storagePdfSplitedDto;
    }

}
