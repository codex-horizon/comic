package com.later.work.service;

import java.util.Map;

public interface ITranslateService {

    Map<String, String> fetchTranslateText(String text);
}
