package com.vinesmario.microservice.server.file.storage;

import com.vinesmario.microservice.client.file.dto.FileImageDto;
import com.vinesmario.microservice.server.common.web.rest.errors.InternalServerErrorException;
import com.vinesmario.microservice.server.file.service.FileImageService;
import com.vinesmario.microservice.server.file.storage.config.LocalStorageConfig;
import com.vinesmario.microservice.server.file.storage.config.StorageProperties;
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
    private final FileImageService service;

    public LocalStorageService(StorageProperties storageProperties, FileImageService service) {
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
    public FileImageDto uploadImage(MultipartFile multipartFile, String path) throws Exception {
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String imageName = uuid + "." + extension;
        String imagePath = config.getRoot() + "/" + imageName;
        FileImageDto fileImageDto = new FileImageDto();
        fileImageDto.setUuid(uuid);
        fileImageDto.setImageName(imageName);
        fileImageDto.setImageAbsolutePath(imagePath);
//        fileImageDto.setImageHeight();
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
        service.create(fileImageDto);
        return fileImageDto;
    }

    @Override
    public FileImageDto uploadImage(InputStream inputStream, String path) throws Exception {
        return null;
    }

    @Override
    public FileImageDto uploadImage(byte[] data, String path) throws Exception {
        return null;
    }

    @Override
    public void deleteObject(String key) throws Exception {

    }
}
