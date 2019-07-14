package com.vinesmario.microservice.server.storage.strategy;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.client.storage.dto.StorageImageDto;
import com.vinesmario.microservice.server.storage.config.QiniuCloudStorageConfig;
import com.vinesmario.microservice.server.storage.config.StorageProperties;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

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
    public void upload(MultipartFile multipartFile, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {
        storageFileDto.setFileAbsoluteUrl(upload(multipartFile.getBytes(), fileRelativePath));
    }

    @Override
    public void upload(InputStream inputStream, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {
        storageFileDto.setFileAbsoluteUrl(upload(IOUtils.toByteArray(inputStream), fileRelativePath));
    }

    @Override
    public void upload(byte[] data, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {
        storageFileDto.setFileAbsoluteUrl(upload(data, fileRelativePath));
    }

    @Override
    public void uploadImage(MultipartFile multipartFile, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {
        storageImageDto.setImageAbsoluteUrl(upload(multipartFile.getBytes(), imageRelativePath));
    }

    @Override
    public void uploadImage(InputStream inputStream, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {
        storageImageDto.setImageAbsoluteUrl(upload(IOUtils.toByteArray(inputStream), imageRelativePath));
    }

    @Override
    public void uploadImage(byte[] data, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {
        storageImageDto.setImageAbsoluteUrl(upload(data, imageRelativePath));
    }

    @Override
    public void deleteObject(String key) throws Exception {

    }

    private String upload(byte[] data, String fileRelativePath) throws Exception {
        if (StringUtils.isNotBlank(config.getNameSpace())) {
            fileRelativePath = config.getNameSpace() + "/" + fileRelativePath;
        }
        UploadManager uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
        String token = Auth.create(config.getAccessKey(), config.getSecretKey()).uploadToken(config.getBucketName());
        Response response = uploadManager.put(data, fileRelativePath, token);
        if (!response.isOK()) {
            throw new RuntimeException("上传七牛出错：" + response.toString());
        }
        String fileAbsoluteUrl = "http://" + config.getDomain() + "/" + fileRelativePath;
        return fileAbsoluteUrl;
    }
}
