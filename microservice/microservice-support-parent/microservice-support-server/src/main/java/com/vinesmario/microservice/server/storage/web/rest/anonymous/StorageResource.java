package com.vinesmario.microservice.server.storage.web.rest.anonymous;

import com.vinesmario.microservice.client.storage.dto.*;
import com.vinesmario.microservice.server.common.web.rest.util.HeaderUtil;
import com.vinesmario.microservice.server.storage.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * 对外暴露，不需要登录校验
 */
@RequestMapping("/api/v1/ignore/storage")
public class StorageResource {

    private final StorageExcelService storageExcelService;
    private final StorageFileService storageFileService;
    private final StorageImageService storageImageService;
    private final StoragePdfService storagePdfService;
    private final StoragePdfSplitedService storagePdfSplitedService;
    private final StorageTemplateService storageTemplateService;

    public StorageResource(StorageExcelService storageExcelService,
                                 StorageFileService storageFileService,
                                 StorageImageService storageImageService,
                                 StoragePdfService storagePdfService,
                                 StoragePdfSplitedService storagePdfSplitedService,
                                 StorageTemplateService storageTemplateService) {
        this.storageExcelService = storageExcelService;
        this.storageFileService = storageFileService;
        this.storageImageService = storageImageService;
        this.storagePdfService = storagePdfService;
        this.storagePdfSplitedService = storagePdfSplitedService;
        this.storageTemplateService = storageTemplateService;
    }

    @ApiOperation(value = "下载Excel", httpMethod = "GET")
    @ApiResponse(code = 200, message = "下载Excel成功", response = String.class)
    @GetMapping(value = "/download/excel/{uuid}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadExcel(@PathVariable("uuid") String uuid)
            throws IOException {
        Optional<StorageExcelDTO> optional = storageExcelService.getByUuid(uuid);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound()
                    .headers(HeaderUtil.createFailureAlert("record not found", 404, "record.not_found", "StorageExcel"))
                    .build();
        } else {
            String fileAbsolutePath = optional.get().getFileAbsolutePath();
            if (StringUtils.isBlank(fileAbsolutePath)) {
                return ResponseEntity.notFound()
                        .headers(HeaderUtil.createFailureAlert("file path is empty", 404, "file_path.empty", "StorageExcel"))
                        .build();
            } else {
                File file = new File(fileAbsolutePath);
                if (!file.exists()) {
                    return ResponseEntity.notFound()
                            .headers(HeaderUtil.createFailureAlert("file not found", 404, "file.not_found", "StorageExcel"))
                            .build();
                }
                return ResponseEntity.ok()
                        .headers(HeaderUtil.createDownload(file.getName()))
                        .body(FileUtils.readFileToByteArray(file));
            }
        }
    }

    @ApiOperation(value = "下载文件", httpMethod = "GET")
    @ApiResponse(code = 200, message = "下载文件成功", response = String.class)
    @GetMapping(value = "/download/file/{uuid}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadFile(@PathVariable("uuid") String uuid)
            throws IOException {
        Optional<StorageFileDTO> optional = storageFileService.getByUuid(uuid);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound()
                    .headers(HeaderUtil.createFailureAlert("record not found", 404, "record.not_found", "StorageFile"))
                    .build();
        } else {
            String fileAbsolutePath = optional.get().getFileAbsolutePath();
            if (StringUtils.isBlank(fileAbsolutePath)) {
                return ResponseEntity.notFound()
                        .headers(HeaderUtil.createFailureAlert("file path is empty", 404, "file_path.empty", "StorageFile"))
                        .build();
            } else {
                File file = new File(fileAbsolutePath);
                if (!file.exists()) {
                    return ResponseEntity.notFound()
                            .headers(HeaderUtil.createFailureAlert("file not found", 404, "file.not_found", "StorageFile"))
                            .build();
                }
                return ResponseEntity.ok()
                        .headers(HeaderUtil.createDownload(file.getName()))
                        .body(FileUtils.readFileToByteArray(file));
            }
        }
    }

    @ApiOperation(value = "下载图片", httpMethod = "GET")
    @ApiResponse(code = 200, message = "下载图片成功", response = String.class)
    @GetMapping(value = "/download/image/{uuid}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadImage(@PathVariable("uuid") String uuid)
            throws IOException {
        Optional<StorageImageDTO> optional = storageImageService.getByUuid(uuid);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound()
                    .headers(HeaderUtil.createFailureAlert("record not found", 404, "record.not_found", "StorageImage"))
                    .build();
        } else {
            String fileAbsolutePath = optional.get().getFileAbsolutePath();
            if (StringUtils.isBlank(fileAbsolutePath)) {
                return ResponseEntity.notFound()
                        .headers(HeaderUtil.createFailureAlert("image path is empty", 404, "file_path.empty", "StorageImage"))
                        .build();
            } else {
                File file = new File(fileAbsolutePath);
                if (!file.exists()) {
                    return ResponseEntity.notFound()
                            .headers(HeaderUtil.createFailureAlert("file not found", 404, "file.not_found", "StorageImage"))
                            .build();
                }
                return ResponseEntity.ok()
                        .headers(HeaderUtil.createDownload(file.getName()))
                        .body(FileUtils.readFileToByteArray(file));
            }
        }
    }

    @ApiOperation(value = "下载PDF", httpMethod = "GET")
    @ApiResponse(code = 200, message = "下载PDF成功", response = String.class)
    @GetMapping(value = "/download/pdf/{uuid}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadPdf(@PathVariable("uuid") String uuid)
            throws IOException {
        Optional<StoragePdfDTO> optional = storagePdfService.getByUuid(uuid);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound()
                    .headers(HeaderUtil.createFailureAlert("record not found", 404, "record.not_found", "StoragePdf"))
                    .build();
        } else {
            String fileAbsolutePath = optional.get().getFileAbsolutePath();
            if (StringUtils.isBlank(fileAbsolutePath)) {
                return ResponseEntity.notFound()
                        .headers(HeaderUtil.createFailureAlert("file path is empty", 404, "file_path.empty", "StoragePdf"))
                        .build();
            } else {
                File file = new File(fileAbsolutePath);
                if (!file.exists()) {
                    return ResponseEntity.notFound()
                            .headers(HeaderUtil.createFailureAlert("file not found", 404, "file.not_found", "StoragePdf"))
                            .build();
                }
                return ResponseEntity.ok()
                        .headers(HeaderUtil.createDownload(file.getName()))
                        .body(FileUtils.readFileToByteArray(file));
            }
        }
    }

    @ApiOperation(value = "下载PDF", httpMethod = "GET")
    @ApiResponse(code = 200, message = "下载PDF成功", response = String.class)
    @GetMapping(value = "/download/pdf-splited/{uuid}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadPdfSplited(@PathVariable("uuid") String uuid)
            throws IOException {
        Optional<StoragePdfSplitedDTO> optional = storagePdfSplitedService.getByUuid(uuid);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound()
                    .headers(HeaderUtil.createFailureAlert("record not found", 404, "record.not_found", "StoragePdfSplited"))
                    .build();
        } else {
            String fileAbsolutePath = optional.get().getFileAbsolutePath();
            if (StringUtils.isBlank(fileAbsolutePath)) {
                return ResponseEntity.notFound()
                        .headers(HeaderUtil.createFailureAlert("file path is empty", 404, "file_path.empty", "StoragePdfSplited"))
                        .build();
            } else {
                File file = new File(fileAbsolutePath);
                if (!file.exists()) {
                    return ResponseEntity.notFound()
                            .headers(HeaderUtil.createFailureAlert("file not found", 404, "file.not_found", "StoragePdfSplited"))
                            .build();
                }
                return ResponseEntity.ok()
                        .headers(HeaderUtil.createDownload(file.getName()))
                        .body(FileUtils.readFileToByteArray(file));
            }
        }
    }

    @ApiOperation(value = "下载模板", httpMethod = "GET")
    @ApiResponse(code = 200, message = "下载模板成功", response = String.class)
    @GetMapping(value = "/download/template/{uuid}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadTemplate(@PathVariable("uuid") String uuid)
            throws IOException {
        Optional<StorageTemplateDTO> optional = storageTemplateService.getByUuid(uuid);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound()
                    .headers(HeaderUtil.createFailureAlert("record not found", 404, "record.not_found", "StorageTemplate"))
                    .build();
        } else {
            String fileAbsolutePath = optional.get().getFileAbsolutePath();
            if (StringUtils.isBlank(fileAbsolutePath)) {
                return ResponseEntity.notFound()
                        .headers(HeaderUtil.createFailureAlert("file path is empty", 404, "file_path.empty", "StorageTemplate"))
                        .build();
            } else {
                File file = new File(fileAbsolutePath);
                if (!file.exists()) {
                    return ResponseEntity.notFound()
                            .headers(HeaderUtil.createFailureAlert("file not found", 404, "file.not_found", "StorageTemplate"))
                            .build();
                }
                return ResponseEntity.ok()
                        .headers(HeaderUtil.createDownload(file.getName()))
                        .body(FileUtils.readFileToByteArray(file));
            }
        }
    }

}
