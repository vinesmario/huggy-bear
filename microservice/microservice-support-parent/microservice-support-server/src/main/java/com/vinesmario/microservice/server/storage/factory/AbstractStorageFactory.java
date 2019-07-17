package com.vinesmario.microservice.server.storage.factory;

import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import org.springframework.web.multipart.MultipartFile;


public abstract class AbstractStorageFactory<T extends StorageFileDto> {

    public abstract T create(MultipartFile multipartFile, Long tenantId) throws Exception;

}
