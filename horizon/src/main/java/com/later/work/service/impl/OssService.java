package com.later.work.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.later.core.configurer.CommonConfigurer;
import com.later.work.service.IOssService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class OssService implements IOssService {

    private final CommonConfigurer commonConfigurer;

    OssService(final CommonConfigurer commonConfigurer) {
        this.commonConfigurer = commonConfigurer;
    }

    @Override
    public String upload(String imageBase64) {
        OSS ossClient = new OSSClientBuilder().build(commonConfigurer.getAlibabaOssEndpoint(), commonConfigurer.getAlibabaOssAccessKey(), commonConfigurer.getAlibabaOssAccessSecret());
        InputStream streamBody = new ByteArrayInputStream(
                Base64.getDecoder().decode(
                        imageBase64.replace("data:image/png;base64,", "")
                )
        );
        String bucketName = "img-fm1100";
        String filename = "";
        String currentDay = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        ossClient.putObject(bucketName, filename, streamBody);
        return "https://" + bucketName + "." + commonConfigurer.getAlibabaOssEndpoint() + "/" + filename;
    }
}
