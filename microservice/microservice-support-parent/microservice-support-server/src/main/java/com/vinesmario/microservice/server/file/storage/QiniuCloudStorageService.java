package com.vinesmario.microservice.server.file.storage;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.vinesmario.microservice.client.file.dto.FileImageDto;
import com.vinesmario.microservice.server.file.storage.config.QiniuCloudStorageConfig;
import com.vinesmario.microservice.server.file.storage.config.StorageProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Lazy
@Service
public class QiniuCloudStorageService extends AbstractStorageService {

    private final QiniuCloudStorageConfig config;

    public QiniuCloudStorageService(StorageProperties storageProperties) {
        if (ObjectUtils.isEmpty(storageProperties.getCloud())) {
            throw new IllegalArgumentException("Property 'file.storage.cloud' is empty ");
        } else if (ObjectUtils.isEmpty(storageProperties.getCloud().getQiniu())) {
            throw new IllegalArgumentException("Property 'file.storage.cloud.qiniu' is empty ");
        }
        this.config = storageProperties.getCloud().getQiniu();
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
        String imageName = multipartFile.getOriginalFilename();
        String imageUrl = "";

        try {
            UploadManager uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
            String token = Auth.create(config.getAccessKey(), config.getSecretKey()).uploadToken(config.getBucketName());
            Response response = uploadManager.put(multipartFile.getBytes(), path, token);
            if (!response.isOK()) {
                throw new RuntimeException("上传七牛出错：" + response.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败，请核对七牛配置信息", e);
        }

//        return config.getDomain() + "/" + path;
//
//        FileImageDto fileImageDto = new FileImageDto();
//        fileImageDto.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
//        fileImageDto.setImageName(imageName);
//        fileImageDto.setImageAbsoluteUrl(imageUrl);
//        fileImageDto.setImageHeight();
        return null;
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
