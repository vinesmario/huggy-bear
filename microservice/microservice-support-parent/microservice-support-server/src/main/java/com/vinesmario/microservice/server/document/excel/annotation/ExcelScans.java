package com.vinesmario.microservice.server.document.excel.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ExcelScans {
    ExcelScan[] value();
}
