package com.vinesmario.microservice.server.document.excel.annotation;

import com.vinesmario.microservice.server.document.excel.impl.ExcelCollector;
import com.vinesmario.microservice.server.document.excel.web.ExcelResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 可以通过配置 document.excel.enabled 来
 * 启用或禁用Excel服务。
 */
@ConditionalOnExpression("${document.excel.enabled:true}")
@ComponentScan(basePackageClasses = {
        ExcelResource.class,
        ExcelCollector.class
})
@Configuration
public class ExcelConfiguration {

}