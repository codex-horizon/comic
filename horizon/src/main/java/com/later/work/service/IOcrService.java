package com.later.work.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IOcrService {

    Map<String, String> fetchOcrText(String imageBase64);
}
