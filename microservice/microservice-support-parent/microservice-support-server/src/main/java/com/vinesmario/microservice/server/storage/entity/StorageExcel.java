package com.vinesmario.microservice.server.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * @author
 * @date
 */
@Data
@Table(name = "storage_excel")
public class StorageExcel extends StorageFile {

}