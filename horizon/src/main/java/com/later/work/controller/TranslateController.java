package com.later.work.controller;

import com.later.common.restful.IResult;
import com.later.common.validated.GroupValidator;
import com.later.work.dto.TranslateDto;
import com.later.work.service.ITranslateService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/translate")
public class TranslateController {

    private final ITranslateService iTranslateService;

    TranslateController(final ITranslateService iTranslateService) {
        this.iTranslateService = iTranslateService;
    }

    @RequestMapping(name = "获取translate文本", path = "/fetchTranslateText", method = RequestMethod.POST)
    IResult<Map<String, String>> fetchTranslateText(@RequestBody @Validated(GroupValidator.Translate.class) TranslateDto translateDto) {
        return IResult.Result.succeeded(iTranslateService.fetchTranslateText(translateDto.getText()));
    }

}
