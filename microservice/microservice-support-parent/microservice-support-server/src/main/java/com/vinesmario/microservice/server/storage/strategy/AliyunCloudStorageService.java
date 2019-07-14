package com.vinesmario.microservice.server.storage.strategy;

import com.aliyun.oss.OSSClient;
import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.client.storage.dto.StorageImageDto;
import com.vinesmario.microservice.server.storage.config.AliyunCloudStorageConfig;
import com.vinesmario.microservice.server.storage.config.StorageProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Lazy
@Service
public class AliyunCloudStorageService extends AbstractStorageService {

    private final AliyunCloudStorageConfig config;

    public AliyunCloudStorageService(StorageProperties storageProperties) {
        if (ObjectUtils.isEmpty(storageProperties.getCloud())) {
            throw new IllegalArgumentException("Property 'file.storage.cloud' is empty ");
        } else if (ObjectUtils.isEmpty(storageProperties.getCloud().getAliyun())) {
            throw new IllegalArgumentException("Property 'file.storage.cloud.aliyun' is empty ");
        }
        this.config = storageProperties.getCloud().getAliyun();
    }

    @Override
    public void upload(MultipartFile multipartFile, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {
        storageFileDto.setFileAbsoluteUrl(upload(multipartFile.getInputStream(), fileRelativePath));
    }

    @Override
    public void upload(InputStream inputStream, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {
        storageFileDto.setFileAbsoluteUrl(upload(inputStream, fileRelativePath));
    }

    @Override
    public void upload(byte[] data, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {
        storageFileDto.setFileAbsoluteUrl(upload(new ByteArrayInputStream(data), fileRelativePath));
    }

    @Override
    public void uploadImage(MultipartFile multipartFile, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {
        storageImageDto.setImageAbsoluteUrl(upload(multipartFile.getInputStream(), imageRelativePath));
    }

    @Override
    public void uploadImage(InputStream inputStream, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {
        storageImageDto.setImageAbsoluteUrl(upload(inputStream, imageRelativePath));
    }

    @Override
    public void uploadImage(byte[] data, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {
        storageImageDto.setImageAbsoluteUrl(upload(new ByteArrayInputStream(data), imageRelativePath));
    }

    @Override
    public void deleteObject(String key) throws Exception {

    }

    private String upload(InputStream inputStream, String fileRelativePath) throws Exception {
        if (StringUtils.isNotBlank(config.getNameSpace())) {
            fileRelativePath = config.getNameSpace() + "/" + fileRelativePath;
        }
        OSSClient client = new OSSClient(config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        client.putObject(config.getBucketName(), fileRelativePath, inputStream);
        String fileAbsoluteUrl = "http://" + config.getDomain() + "/" + fileRelativePath;
        return fileAbsoluteUrl;
    }
}
