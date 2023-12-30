package com.later.work.controller;

import com.later.common.converter.IConverter;
import com.later.common.restful.IPageable;
import com.later.common.restful.IResult;
import com.later.common.validated.GroupValidator;
import com.later.work.bo.MenuBo;
import com.later.work.dto.MenuDto;
import com.later.work.qry.MenuQry;
import com.later.work.service.IMenuService;
import com.later.work.vo.MenuVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final IMenuService iMenuService;

    private final IConverter iConverter;

    MenuController(final IMenuService iMenuService,
                   final IConverter iConverter) {
        this.iMenuService = iMenuService;
        this.iConverter = iConverter;
    }

    @RequestMapping(name = "菜单列表", path = "/list", method = RequestMethod.POST)
    IResult<IPageable<List<MenuVo>>> list(@RequestBody MenuQry menuQry) {
        return IResult.Result.succeeded(iMenuService.list(menuQry));
    }

    @RequestMapping(name = "菜单添加", path = "/add", method = RequestMethod.POST)
    IResult<Long> add(@RequestBody @Validated(GroupValidator.Create.class) MenuDto menuDto) {
        return IResult.Result.succeeded(iMenuService.add(iConverter.convert(menuDto, MenuBo.class)));
    }

    @RequestMapping(name = "菜单更新", path = "/update", method = RequestMethod.POST)
    IResult<Long> update(@RequestBody @Validated(GroupValidator.Modify.class) MenuDto menuDto) {
        return IResult.Result.succeeded(iMenuService.update(iConverter.convert(menuDto, MenuBo.class)));
    }

}
