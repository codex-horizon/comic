package com.later.work.controller;

import com.later.common.converter.IConverter;
import com.later.common.restful.IPageable;
import com.later.common.restful.IResult;
import com.later.common.validated.GroupValidator;
import com.later.work.bo.UserBo;
import com.later.work.dto.UserDto;
import com.later.work.qry.UserQry;
import com.later.work.service.IUserService;
import com.later.work.vo.UserVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(name = "用户分页", path = "/pageable", method = RequestMethod.POST)
    IResult<IPageable<List<UserVo>>> pageable(@RequestBody UserQry userQry) {
        return IResult.Result.succeeded(iUserService.pageable(userQry));
    }

    @RequestMapping(name = "用户添加", path = "/add", method = RequestMethod.POST)
    IResult<Long> add(@RequestBody @Validated(GroupValidator.Create.class) UserDto userDto) {
        return IResult.Result.succeeded(iUserService.add(iConverter.convert(userDto, UserBo.class)));
    }

    @RequestMapping(name = "用户更新", path = "/update", method = RequestMethod.POST)
    IResult<Long> update(@RequestBody @Validated(GroupValidator.Modify.class) UserDto userDto) {
        return IResult.Result.succeeded(iUserService.update(iConverter.convert(userDto, UserBo.class)));
    }

    @RequestMapping(name = "用户登录", path = "/login", method = RequestMethod.POST)
    IResult<?> login(@RequestBody @Validated(GroupValidator.Login.class) UserDto userDto) {
        return IResult.Result.succeeded(iUserService.login(iConverter.convert(userDto, UserBo.class)));
    }

    @RequestMapping(name = "用户删除", path = "/delete/{id}", method = RequestMethod.POST)
    IResult<Boolean> delete(@PathVariable("id")Long id) {
        return IResult.Result.succeeded(iUserService.delete(id));
    }

}
