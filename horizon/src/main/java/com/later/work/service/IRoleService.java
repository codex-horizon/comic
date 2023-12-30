package com.later.work.service;

import com.later.common.restful.IPageable;
import com.later.work.bo.RoleBo;
import com.later.work.dto.RoleDto;
import com.later.work.qry.RoleQry;
import com.later.work.vo.RoleVo;

import java.util.List;

public interface IRoleService {

    IPageable<List<RoleVo>> pageable(RoleQry roleQry);

    Long add(RoleBo roleBo);

    Long update(RoleBo roleBo);

    List<RoleVo> list(RoleQry roleQry);

    Boolean delete(Long id);
}
