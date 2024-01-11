package com.later.work.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.later.core.configurer.CommonConfigurer;
import com.later.work.service.IOssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Base64;

@Slf4j
@Service
public class OssService implements IOssService {

    private final CommonConfigurer commonConfigurer;

    OssService(final CommonConfigurer commonConfigurer) {
        this.commonConfigurer = commonConfigurer;
    }

    /**
     * https://ram.console.aliyun.com/manage/ak?spm=a2c8b.12215514.top-nav.dak.5aba336a0aWFl6
     */
    @Override
    public String upload(String imageBase64, String uri) {
        OSS ossClient = new OSSClientBuilder().build(
                commonConfigurer.getAlibabaOssEndpoint(),
                commonConfigurer.getAlibabaOssAccessKey(),
                commonConfigurer.getAlibabaOssAccessSecret()
        );
        String bucketName = "img-fm1100";
        String objectName = "copy/" + uri;
        try {

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(
                    Base64.getDecoder().decode(
                            imageBase64.replace("data:image/png;base64,", "")
                    )
            ));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setHeader("x-oss-forbid-overwrite", Boolean.FALSE);
            putObjectRequest.setMetadata(metadata);

            ossClient.putObject(putObjectRequest);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return objectName;
    }
}
