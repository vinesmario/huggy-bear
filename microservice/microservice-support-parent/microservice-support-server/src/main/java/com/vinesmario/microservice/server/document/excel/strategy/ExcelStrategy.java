package com.vinesmario.microservice.server.document.excel.strategy;

public interface ExcelStrategy {

    void doImport() throws Exception;

    void doExport() throws Exception;

}
