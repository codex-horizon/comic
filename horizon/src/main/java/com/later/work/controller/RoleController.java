package com.later.work.controller;

import com.later.common.converter.IConverter;
import com.later.common.restful.IPageable;
import com.later.common.restful.IResult;
import com.later.common.validated.GroupValidator;
import com.later.work.bo.RoleBo;
import com.later.work.dto.RoleDto;
import com.later.work.qry.RoleQry;
import com.later.work.service.IRoleService;
import com.later.work.vo.RoleVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final IRoleService iRoleService;

    private final IConverter iConverter;

    RoleController(final IRoleService iRoleService,
                   final IConverter iConverter) {
        this.iRoleService = iRoleService;
        this.iConverter = iConverter;
    }

    @RequestMapping(name = "角色列表", path = "/list", method = RequestMethod.POST)
    IResult<List<RoleVo>> list(@RequestBody RoleQry roleQry) {
        return IResult.Result.succeeded(iRoleService.list(roleQry));
    }

    @RequestMapping(name = "角色分页", path = "/pageable", method = RequestMethod.POST)
    IResult<IPageable<List<RoleVo>>> pageable(@RequestBody RoleQry roleQry) {
        return IResult.Result.succeeded(iRoleService.pageable(roleQry));
    }

    @RequestMapping(name = "角色添加", path = "/add", method = RequestMethod.POST)
    IResult<Long> add(@RequestBody @Validated(GroupValidator.Create.class) RoleDto roleDto) {
        return IResult.Result.succeeded(iRoleService.add(iConverter.convert(roleDto, RoleBo.class)));
    }

    @RequestMapping(name = "角色更新", path = "/update", method = RequestMethod.POST)
    IResult<Long> update(@RequestBody @Validated(GroupValidator.Modify.class) RoleDto roleDto) {
        return IResult.Result.succeeded(iRoleService.update(iConverter.convert(roleDto, RoleBo.class)));
    }

    @RequestMapping(name = "角色删除", path = "/delete/{id}", method = RequestMethod.POST)
    IResult<Boolean> delete(@PathVariable("id")Long id) {
        return IResult.Result.succeeded(iRoleService.delete(id));
    }
}
