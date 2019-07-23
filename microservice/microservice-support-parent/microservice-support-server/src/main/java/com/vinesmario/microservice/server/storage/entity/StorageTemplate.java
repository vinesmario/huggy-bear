package com.vinesmario.microservice.server.storage.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author
 * @date
 */
@Data
@Table(name = "storage_template")
public class StorageTemplate extends StorageFile {

}