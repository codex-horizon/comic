package com.later.work.service;

import com.later.common.restful.IPageable;
import com.later.work.bo.UserBo;
import com.later.work.dto.UserDto;
import com.later.work.qry.UserQry;
import com.later.work.vo.UserVo;

import java.util.List;

public interface IUserService {

    IPageable<List<UserVo>> pageable(UserQry userQry);

    Long add(UserBo userBo);

    Long update(UserBo userBo);

    Boolean delete(Long id);

    Object authentication(UserBo userBo);
}
