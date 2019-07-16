package com.vinesmario.microservice.server.storage.strategy;

import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.client.storage.dto.StorageImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public abstract class StorageStrategy {
    /**
     * 文件上传
     *
     * @param multipartFile
     * @param fileRelativePath
     * @return 返回http地址
     * @throws Exception
     */
    public abstract void upload(MultipartFile multipartFile, String fileRelativePath, StorageFileDto storageFileDto) throws Exception;

    /**
     * 文件上传
     *
     * @param inputStream      字节流
     * @param fileRelativePath
     * @return 返回http地址
     * @throws Exception
     */
    public abstract void upload(InputStream inputStream, String fileRelativePath, StorageFileDto storageFileDto) throws Exception;

    /**
     * 文件上传
     *
     * @param data             文件字节数组
     * @param fileRelativePath
     * @return 返回http地址
     * @throws Exception
     */
    public abstract void upload(byte[] data, String fileRelativePath, StorageFileDto storageFileDto) throws Exception;

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param imageRelativePath
     * @return 返回http地址
     * @throws Exception
     */
    public abstract void uploadImage(MultipartFile multipartFile, String imageRelativePath, StorageImageDto storageImageDto) throws Exception;

    /**
     * 文件上传
     *
     * @param inputStream       字节流
     * @param imageRelativePath
     * @return 返回http地址
     * @throws Exception
     */
    public abstract void uploadImage(InputStream inputStream, String imageRelativePath, StorageImageDto storageImageDto) throws Exception;

    /**
     * 文件上传
     *
     * @param data              文件字节数组
     * @param imageRelativePath
     * @return 返回http地址
     * @throws Exception
     */
    public abstract void uploadImage(byte[] data, String imageRelativePath, StorageImageDto storageImageDto) throws Exception;

    /**
     * 删除单个文件:
     *
     * @param key
     * @return
     * @throws Exception
     */
    public abstract void deleteObject(String key) throws Exception;
}
