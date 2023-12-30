package com.later.work.controller;

import com.later.common.converter.IConverter;
import com.later.common.restful.IPageable;
import com.later.common.restful.IResult;
import com.later.work.qry.ComicQry;
import com.later.work.service.IComicService;
import com.later.work.vo.ComicVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comic")
public class ComicController {

    private final IComicService iComicService;

    private final IConverter iConverter;

    ComicController(final IComicService iComicService,
                    final IConverter iConverter) {
        this.iComicService = iComicService;
        this.iConverter = iConverter;
    }

    @RequestMapping(name = "漫画分页", path = "/pageable", method = RequestMethod.POST)
    IResult<IPageable<List<ComicVo>>> pageable(@RequestBody ComicQry comicQry) {
        return IResult.Result.succeeded(iComicService.pageable(comicQry));
    }

    @RequestMapping(name = "漫画列表", path = "/list", method = RequestMethod.POST)
    IResult<List<ComicVo>> list(@RequestBody ComicQry comicQry) {
        return IResult.Result.succeeded(iComicService.list(comicQry));
    }


}
