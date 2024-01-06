package com.later.work.service.impl;

import com.alibaba.fastjson2.JSON;
import com.aliyun.ocr_api20210707.Client;
import com.aliyun.ocr_api20210707.models.RecognizeKoreanRequest;
import com.aliyun.ocr_api20210707.models.RecognizeKoreanResponse;
import com.aliyun.teaopenapi.models.Config;
import com.later.common.helper.FetchHelper;
import com.later.core.configurer.CommonConfigurer;
import com.later.work.service.IOcrService;
import com.later.work.service.ITranslateService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Map;

@Service
public class OcrService implements IOcrService {

    private final Client OcrClient;

    OcrService(final CommonConfigurer commonConfigurer) throws Exception {
        Config config = new Config();
        config.setAccessKeyId(commonConfigurer.getAlibabaOcrAccessKey());
        config.setAccessKeySecret(commonConfigurer.getAlibabaOcrAccessSecret());
        config.endpoint = commonConfigurer.getAlibabaOcrEndpoint();
        OcrClient = new Client(config);
    }

    @Override
    public Map<String, String> fetchOcrText(String imageBase64) {
        try {
            ByteArrayInputStream streamBody = new ByteArrayInputStream(
                    Base64.getDecoder().decode(
                            imageBase64.replace("data:image/png;base64,", "")
                    )
            );
            RecognizeKoreanRequest recognizeKoreanRequest = new RecognizeKoreanRequest();
            recognizeKoreanRequest.setBody(streamBody);
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
            RecognizeKoreanResponse recognizeKoreanResponse = OcrClient.recognizeKoreanWithOptions(recognizeKoreanRequest, runtime);
            String text = JSON.parseObject(recognizeKoreanResponse.getBody().getData()).get("content").toString();
            ITranslateService iTranslateService = FetchHelper.getBean(ITranslateService.class);
            return iTranslateService.fetchTranslateText(text);
        } catch (Exception ignored) {
        }
        return null;
    }
}
