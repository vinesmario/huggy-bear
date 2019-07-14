package com.vinesmario.microservice.server.storage.strategy;

import com.vinesmario.microservice.client.storage.dto.StorageImageDto;
import com.vinesmario.microservice.server.common.web.rest.errors.InternalServerErrorException;
import com.vinesmario.microservice.server.storage.service.StorageImageService;
import com.vinesmario.microservice.server.storage.config.LocalStorageConfig;
import com.vinesmario.microservice.server.storage.config.StorageProperties;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Lazy
@Service
public class LocalStorageService extends AbstractStorageService {

    private final LocalStorageConfig config;
    private final StorageImageService service;

    public LocalStorageService(StorageProperties storageProperties, StorageImageService service) {
        if (ObjectUtils.isEmpty(storageProperties.getLocal())) {
            throw new IllegalArgumentException("Property 'file.storage.local' is empty ");
        } else if (ObjectUtils.isEmpty(storageProperties.getLocal().getRoot())) {
            throw new IllegalArgumentException("Property 'file.storage.local.root' is empty ");
        }
        this.config = storageProperties.getLocal();
        this.service = service;
    }

    @Override
    public String upload(MultipartFile multipartFile, String path) throws Exception {
        return null;
    }

    @Override
    public String upload(InputStream inputStream, String path) throws Exception {
        return null;
    }

    @Override
    public String upload(byte[] data, String path) throws Exception {
        return null;
    }

    @Override
    public StorageImageDto uploadImage(MultipartFile multipartFile, String path) throws Exception {
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String imageName = uuid + "." + extension;
        String imagePath = config.getRoot() + "/" + imageName;
        StorageImageDto storageImageDto = new StorageImageDto();
        storageImageDto.setUuid(uuid);
        storageImageDto.setImageName(imageName);
        storageImageDto.setImageAbsolutePath(imagePath);
//        storageImageDto.setImageHeight();
        try {
            File file = new File(imagePath);
            //判断路径是否存在，如果不存在就创建一个
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // TODO 图片高度和宽度、大小、MD5、SHA1
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerErrorException(e.getMessage());
        }
        service.create(storageImageDto);
        return storageImageDto;
    }

    @Override
    public StorageImageDto uploadImage(InputStream inputStream, String path) throws Exception {
        return null;
    }

    @Override
    public StorageImageDto uploadImage(byte[] data, String path) throws Exception {
        return null;
    }

    @Override
    public void deleteObject(String key) throws Exception {

    }
}
