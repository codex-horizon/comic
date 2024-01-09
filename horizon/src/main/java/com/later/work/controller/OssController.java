package com.later.work.controller;

import com.later.common.restful.IResult;
import com.later.common.validated.GroupValidator;
import com.later.work.dto.OssDto;
import com.later.work.service.IOssService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oss")
public class OssController {

    private final IOssService iOssService;

    OssController(final IOssService iOssService) {
        this.iOssService = iOssService;
    }

    @RequestMapping(name = "文件上传oss", path = "/upload", method = RequestMethod.POST)
    IResult<String> upload(@RequestBody @Validated(GroupValidator.Oss.class) OssDto ossDto) {
        return IResult.Result.succeeded(iOssService.upload(ossDto.getImage()));
    }


}
