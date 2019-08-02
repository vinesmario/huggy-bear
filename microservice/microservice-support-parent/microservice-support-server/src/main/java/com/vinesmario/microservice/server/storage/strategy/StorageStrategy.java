package com.vinesmario.microservice.server.storage.strategy;

import com.vinesmario.microservice.client.storage.dto.StorageFileDTO;
import com.vinesmario.microservice.client.storage.dto.StorageImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public abstract class StorageStrategy {

    /**
     * 数据持久化入库
     *
     * @return
     */
    public abstract boolean isPersistent();

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param fileRelativePath
     * @throws Exception
     */
    public abstract <T extends StorageFileDTO> void upload(MultipartFile multipartFile, String fileRelativePath, T dto) throws Exception;

    /**
     * 文件上传
     *
     * @param inputStream      字节流
     * @param fileRelativePath
     * @throws Exception
     */
    public abstract <T extends StorageFileDTO> void upload(InputStream inputStream, String fileRelativePath, T dto) throws Exception;

    /**
     * 文件上传
     *
     * @param data             文件字节数组
     * @param fileRelativePath
     * @throws Exception
     */
    public abstract <T extends StorageFileDTO> void upload(byte[] data, String fileRelativePath, T dto) throws Exception;

    /**
     * 文件上传
     *
     * @param inputStream      字节流
     * @param fileRelativePath
     * @throws Exception
     */
    public abstract String upload(InputStream inputStream, String fileRelativePath) throws Exception;

    /**
     * 删除单个文件:
     *
     * @param key
     * @return
     * @throws Exception
     */
    public abstract void deleteObject(String key) throws Exception;
}
