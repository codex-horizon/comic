package com.later.work.controller;

import com.later.common.restful.IResult;
import com.later.common.validated.GroupValidator;
import com.later.work.dto.OssRecoverDto;
import com.later.work.dto.OssRollBackRecordDto;
import com.later.work.dto.OssUploadDto;
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
    IResult<String> upload(@RequestBody @Validated(GroupValidator.Oss.class) OssUploadDto ossUploadDto) {
        return IResult.Result.succeeded(iOssService.upload(ossUploadDto.getId(), ossUploadDto.getImage(), ossUploadDto.getUri()));
    }

    @RequestMapping(name = "文件恢复oss", path = "/recover", method = RequestMethod.POST)
    IResult<String> recover(@RequestBody @Validated(GroupValidator.Oss.class) OssRecoverDto ossRecoverDto) {
        return IResult.Result.succeeded(iOssService.recover(ossRecoverDto.getId()));
    }

    @RequestMapping(name = "文件回滚记录oss", path = "/rollBackRecord", method = RequestMethod.POST)
    IResult<String> rollBackRecord(@RequestBody @Validated(GroupValidator.Oss.class) OssRollBackRecordDto ossRollBackRecordDto) {
        return IResult.Result.succeeded(iOssService.rollBackRecord(ossRollBackRecordDto.getId(), ossRollBackRecordDto.getImage(), ossRollBackRecordDto.getUri(), ossRollBackRecordDto.isHasRollBackRecord()));
    }

}
