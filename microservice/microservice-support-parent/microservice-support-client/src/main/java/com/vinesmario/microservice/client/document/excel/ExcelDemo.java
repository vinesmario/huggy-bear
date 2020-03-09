package com.vinesmario.microservice.client.document.excel;

import com.vinesmario.microservice.client.document.excel.annotation.Excel;
import com.vinesmario.microservice.client.document.excel.annotation.ExcelColumn;
import com.vinesmario.microservice.client.document.excel.annotation.ExcelColumns;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Excel(sheetIndex = 0)
@Data
public class ExcelDemo {

    @ExcelColumns({
            @ExcelColumn(name = "field1", portType = ExcelColumn.PortType.BOTH, index = 0),
            @ExcelColumn(name = "field2", portType = ExcelColumn.PortType.BOTH, index = 1)
    })
    private Integer field1;
    private Long field2;
    @ExcelColumn(portType = ExcelColumn.PortType.IMPORT, index = 2)
    private String field3;
    @ExcelColumn(portType = ExcelColumn.PortType.IMPORT, index = 3)
    private String field4;
    @ExcelColumn(portType = ExcelColumn.PortType.IMPORT, index = 4)
    private Date field5;
    @ExcelColumn(portType = ExcelColumn.PortType.IMPORT, index = 5)
    private LocalDate field6;
    @ExcelColumn(portType = ExcelColumn.PortType.IMPORT, index = 6)
    private BigDecimal field7;
    @ExcelColumn(portType = ExcelColumn.PortType.IMPORT, index = 7)
    private BigDecimal field8;
    @ExcelColumn(portType = ExcelColumn.PortType.IMPORT, index = 8)
    private String field9;
    @ExcelColumn(portType = ExcelColumn.PortType.IMPORT, index = 9)
    private String field10;
    private Boolean field11;
    private String field99;

}
