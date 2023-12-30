package com.later.work.service;

import com.later.common.restful.IPageable;
import com.later.work.bo.MenuBo;
import com.later.work.qry.MenuQry;
import com.later.work.vo.MenuVo;

import java.util.List;

public interface IMenuService {

    Long update(MenuBo menuBo);

    Long add(MenuBo menuBo);

    IPageable<List<MenuVo>> pageable(MenuQry menuQry);

    List<MenuBo> list(MenuQry menuQry);

    Boolean delete(Long id);
}
