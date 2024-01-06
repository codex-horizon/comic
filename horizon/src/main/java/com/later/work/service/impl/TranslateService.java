package com.later.work.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.later.core.configurer.CommonConfigurer;
import com.later.work.service.ITranslateService;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranslateService implements ITranslateService {

    private static final OkHttpClient KoHttpClient = new OkHttpClient().newBuilder().build();

    private final CommonConfigurer commonConfigurer;

    TranslateService(final CommonConfigurer commonConfigurer) {
        this.commonConfigurer = commonConfigurer;
    }

    @Override
    public Map<String, String> fetchTranslateText(String text) {
        JSONObject tokenObject = fetchToken();
        if (ObjectUtils.isEmpty(tokenObject)) {
            return null;
        }
        try {
            Map<String, String> params = new HashMap<>();
            params.put("from", "kor");
            params.put("to", "zh");
            params.put("q", text);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(params));
            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/rpc/2.0/mt/texttrans/v1?access_token=" + tokenObject.get("access_token").toString())
                    .method("POST", requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = KoHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            return ObjectUtils.isEmpty(responseBody) ? null : JSONObject.parseObject(responseBody.string(), new TypeReference<Map<String, String>>() {
            });
        } catch (Exception ignored) {
        }
        return null;
    }

    private JSONObject fetchToken() {
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                    String.format("grant_type=client_credentials&client_id=%s&client_secret=%s",
                            commonConfigurer.getBaiduTranslateAccessKey(),
                            commonConfigurer.getBaiduTranslateAccessSecret())
            );
            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/oauth/2.0/token")
                    .method("POST", requestBody)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = KoHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            return ObjectUtils.isEmpty(responseBody) ? null : JSON.parseObject(responseBody.string(), JSONObject.class);
        } catch (Exception ignored) {
        }
        return null;
    }

}
