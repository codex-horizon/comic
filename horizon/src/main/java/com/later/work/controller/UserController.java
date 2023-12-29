package com.later.work.controller;

import com.later.common.converter.IConverter;
import com.later.common.restful.IPageable;
import com.later.common.restful.IResult;
import com.later.work.qry.UserQry;
import com.later.work.service.IUserService;
import com.later.work.vo.UserVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService iUserService;

    private final IConverter iConverter;

    UserController(final IUserService iUserService,
                   final IConverter iConverter) {
        this.iUserService = iUserService;
        this.iConverter = iConverter;
    }

    @RequestMapping(name = "用户列表", path = "/list", method = RequestMethod.POST)
    IResult<IPageable<List<UserVo>>> list(@RequestBody UserQry userQry) {
        return IResult.Result.succeeded(iUserService.list(userQry));
    }


}
