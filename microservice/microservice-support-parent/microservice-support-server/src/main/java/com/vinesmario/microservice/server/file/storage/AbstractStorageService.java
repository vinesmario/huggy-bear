package com.vinesmario.microservice.server.file.storage;

import com.vinesmario.microservice.client.file.dto.FileImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public abstract class AbstractStorageService {
    /**
     * 文件上传
     *
     * @param multipartFile
     * @param path
     * @return 返回http地址
     * @throws Exception
     */
    public abstract String upload(MultipartFile multipartFile, String path) throws Exception;

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path
     * @return 返回http地址
     * @throws Exception
     */
    public abstract String upload(InputStream inputStream, String path) throws Exception;

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path
     * @return 返回http地址
     * @throws Exception
     */
    public abstract String upload(byte[] data, String path) throws Exception;

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param path
     * @return 返回http地址
     * @throws Exception
     */
    public abstract FileImageDto uploadImage(MultipartFile multipartFile, String path) throws Exception;

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path
     * @return 返回http地址
     * @throws Exception
     */
    public abstract FileImageDto uploadImage(InputStream inputStream, String path) throws Exception;

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path
     * @return 返回http地址
     * @throws Exception
     */
    public abstract FileImageDto uploadImage(byte[] data, String path) throws Exception;

    /**
     * 删除单个文件:
     *
     * @param key
     * @return
     * @throws Exception
     */
    public abstract void deleteObject(String key) throws Exception;
}
