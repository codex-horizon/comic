package com.later.work.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.later.common.helper.FetchHelper;
import com.later.core.configurer.CommonConfigurer;
import com.later.work.bo.ComicPictureBo;
import com.later.work.service.IComicPictureService;
import com.later.work.service.IOssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.Base64;

@Slf4j
@Service
public class OssService implements IOssService {

    private final CommonConfigurer commonConfigurer;

    private final static String OssBucketName = "img-fm1100";

    OssService(final CommonConfigurer commonConfigurer) {
        this.commonConfigurer = commonConfigurer;
    }

    /**
     * https://ram.console.aliyun.com/manage/ak?spm=a2c8b.12215514.top-nav.dak.5aba336a0aWFl6
     */
    @Override
    public String upload(Long id, String imageBase64, String uri) {
        OSS ossClient = new OSSClientBuilder().build(
                commonConfigurer.getAlibabaOssEndpoint(),
                commonConfigurer.getAlibabaOssAccessKey(),
                commonConfigurer.getAlibabaOssAccessSecret()
        );

        IComicPictureService iComicPictureService = FetchHelper.getBean(IComicPictureService.class);
        ComicPictureBo comicPictureBo = new ComicPictureBo();
        comicPictureBo.setId(id);
        if (StringUtils.hasText(uri)) {
            if (uri.contains("lastModifiedUrl/")) {
                comicPictureBo.setLastModifiedUrl(uri);
            } else {
                comicPictureBo.setLastModifiedUrl("lastModifiedUrl/" + uri);
            }
        }
        iComicPictureService.uploadSendUpdate(comicPictureBo);

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    commonConfigurer.getAlibabaOssBucketName(),
                    comicPictureBo.getLastModifiedUrl(),
                    new ByteArrayInputStream(
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
        return comicPictureBo.getLastModifiedUrl();
    }

    @Override
    public String rollBackRecord(Long id, String imageBase64, String uri, boolean hasRollBackRecord) {
        OSS ossClient = new OSSClientBuilder().build(
                commonConfigurer.getAlibabaOssEndpoint(),
                commonConfigurer.getAlibabaOssAccessKey(),
                commonConfigurer.getAlibabaOssAccessSecret()
        );

        IComicPictureService iComicPictureService = FetchHelper.getBean(IComicPictureService.class);
        ComicPictureBo comicPictureBo = iComicPictureService.fetchById(id);
        comicPictureBo.setRollBackRecord(comicPictureBo.getRollBackRecord() + 1);
        iComicPictureService.save(comicPictureBo);

        final String finalURI = "rollBackRecord/" + comicPictureBo.getRollBackRecord() + "/" + uri;
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    commonConfigurer.getAlibabaOssBucketName(),
                    finalURI,
                    new ByteArrayInputStream(
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
        return finalURI;
    }

    @Override
    public String recover(Long id) {
        IComicPictureService iComicPictureService = FetchHelper.getBean(IComicPictureService.class);
        return iComicPictureService.recover(id);
    }
}
