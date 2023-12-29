package com.later.work.service;

import com.later.common.restful.IPageable;
import com.later.work.qry.UserQry;
import com.later.work.vo.UserVo;

import java.util.List;

public interface IUserService {

    IPageable<List<UserVo>> list(UserQry userQry);

}
