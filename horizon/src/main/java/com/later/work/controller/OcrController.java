package com.later.work.controller;

import com.later.common.restful.IResult;
import com.later.common.validated.GroupValidator;
import com.later.work.dto.OcrDto;
import com.later.work.service.IOcrService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 小语种： https://next.api.aliyun.com/api/ocr-api/2021-07-07/RecognizeKorean?sdkStyle=dara&tab=DEMO&lang=JAVA
 */
@RestController
@RequestMapping("/ocr")
public class OcrController {

    private final IOcrService iOcrService;

    OcrController(final IOcrService iOcrService) {
        this.iOcrService = iOcrService;
    }

    @RequestMapping(name = "获取ocr文本", path = "/fetchOcrText", method = RequestMethod.POST)
    IResult<String> fetchOcrText(@RequestBody @Validated(GroupValidator.Ocr.class) OcrDto ocrDto) {
        return IResult.Result.succeeded(iOcrService.fetchOcrText(ocrDto.getImage()));
    }

}
