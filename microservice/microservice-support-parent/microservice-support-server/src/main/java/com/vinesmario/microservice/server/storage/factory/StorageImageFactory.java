package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.storage.dto.StorageImageDto;
import com.vinesmario.microservice.server.common.util.SpringContextUtil;
import com.vinesmario.microservice.server.storage.service.StorageImageService;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategy;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategyFactory;
import com.vinesmario.microservice.server.storage.web.rest.StorageImageResource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class StorageImageFactory extends AbstractStorageFactory<StorageImageDto> {

    @Override
    public StorageImageDto create(MultipartFile multipartFile, Long tenantId) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String imageName = uuid + "." + extension;
        String imageRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + imageName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            imageRelativePath = tenantId + "/" + imageRelativePath;
        }

        StorageImageDto storageImageDto = new StorageImageDto();
        storageImageDto.setTenantId(tenantId);
        storageImageDto.setUuid(uuid);
        storageImageDto.setFileExtension(extension);
        storageImageDto.setFileName(imageName);
        storageImageDto.setFileSize(multipartFile.getSize());
        // 文件MD5、SHA1
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        storageImageDto.setFileMd5Hex(md5Hex);
        storageImageDto.setFileSha1Hex(sha1Hex);

        // 选择文件上传策略
        StorageStrategy storageStrategy = StorageStrategyFactory.build();
        storageStrategy.upload(multipartFile, imageRelativePath, storageImageDto);

        // 图片高度、宽度
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        storageImageDto.setImageWidth(bufferedImage.getWidth());
        storageImageDto.setImageHeight(bufferedImage.getHeight());

        // 文件访问绝对url为空，补充文件访问相对url
        if (StringUtils.isBlank(storageImageDto.getFileAbsoluteUrl())) {
            String url = StorageImageResource.class.getAnnotation(RequestMapping.class).value()[0];
            url += StorageImageResource.class.getMethod("download", String.class).getAnnotation(GetMapping.class).value()[0];
            storageImageDto.setFileRelativeUrl(url.replace("{uuid}", storageImageDto.getUuid()));
//            storageImageDto.setFileRelativeUrl("/api/v1/storage_image/download/{uuid}".replace("{uuid}", storageImageDto.getUuid()));
        }

        // 文件记录持久化
        if (storageStrategy.isPersistent()) {
            StorageImageService service = SpringContextUtil.getBean(StorageImageService.class);
            service.create(storageImageDto);
        }

        storageImageDto.setFileAbsolutePath(null);
        return storageImageDto;
    }

}
