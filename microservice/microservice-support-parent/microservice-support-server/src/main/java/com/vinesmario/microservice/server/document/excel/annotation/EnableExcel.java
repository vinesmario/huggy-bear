package com.vinesmario.microservice.server.document.excel.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ExcelConfiguration.class)
public @interface EnableExcel {

}