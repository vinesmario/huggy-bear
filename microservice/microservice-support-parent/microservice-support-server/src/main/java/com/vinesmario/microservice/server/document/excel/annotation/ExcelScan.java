package com.vinesmario.microservice.server.document.excel.annotation;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Repeatable(ExcelScans.class)
public @interface ExcelScan {
    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    Class<? extends ScopeMetadataResolver> scopeResolver() default AnnotationScopeMetadataResolver.class;

    ScopedProxyMode scopedProxy() default ScopedProxyMode.DEFAULT;

    String resourcePattern() default "**/*.class";

    boolean useDefaultFilters() default true;

    ExcelScan.Filter[] includeFilters() default {};

    ExcelScan.Filter[] excludeFilters() default {};

    boolean lazyInit() default false;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    public @interface Filter {
        FilterType type() default FilterType.ANNOTATION;

        @AliasFor("classes")
        Class<?>[] value() default {};

        @AliasFor("value")
        Class<?>[] classes() default {};

        String[] pattern() default {};
    }
}

