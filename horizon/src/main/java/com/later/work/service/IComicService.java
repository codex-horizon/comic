package com.later.work.service;

import com.later.common.restful.IPageable;
import com.later.work.qry.ComicQry;
import com.later.work.vo.ComicVo;

import java.util.List;

public interface IComicService {

    IPageable<List<ComicVo>> pageable(ComicQry comicQry);

    List<ComicVo> list(ComicQry comicQry);
}
