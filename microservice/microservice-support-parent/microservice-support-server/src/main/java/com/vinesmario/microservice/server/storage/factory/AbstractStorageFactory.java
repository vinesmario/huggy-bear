package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.storage.dto.IStorageDTO;
import com.vinesmario.microservice.client.storage.dto.StorageFileDTO;
import org.springframework.web.multipart.MultipartFile;


public abstract class AbstractStorageFactory<T extends IStorageDTO> {

    public abstract T create(MultipartFile multipartFile, Long tenantId) throws Exception;

}
