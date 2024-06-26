package com.later.core.configurer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.io.Serializable;

@Data
@Configuration
@PropertySources({
        @PropertySource(
                name = "common-${spring.profiles.active}.properties", value = {"classpath:configurer/common-${spring.profiles.active}.properties"},
                encoding = "UTF-8", ignoreResourceNotFound = false
        ),
        @PropertySource(
                name = "common-${spring.profiles.active}.properties", value = {"classpath:configurer/common-${spring.profiles.active}.properties"},
                encoding = "UTF-8", ignoreResourceNotFound = false
        )
})
public class CommonConfigurer implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Value("${application.name}")
    private String applicationName;

    @Value("${request-white.ignored-uris}")
    private String[] requestWhiteIgnoredUris;

    @Value("${alibaba.ocr.accessKey}")
    private String alibabaOcrAccessKey;

    @Value("${alibaba.ocr.accessSecret}")
    private String alibabaOcrAccessSecret;

    @Value(("${alibaba.ocr.endpoint}"))
    private String alibabaOcrEndpoint;

    @Value("${alibaba.oss.accessKey}")
    private String alibabaOssAccessKey;

    @Value("${alibaba.oss.accessSecret}")
    private String alibabaOssAccessSecret;

    @Value(("${alibaba.oss.endpoint}"))
    private String alibabaOssEndpoint;

    @Value(("${alibaba.oss.bucketName}"))
    private String alibabaOssBucketName;

    @Value("${baidu.translate.accessKey}")
    private String baiduTranslateAccessKey;

    @Value("${baidu.translate.accessSecret}")
    private String baiduTranslateAccessSecret;

    @Value("${fileSystemPath.mapping.resourceLocations}")
    private String[] fileSystemPathMappingResourceLocations;

}
