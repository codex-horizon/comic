package com.later.work.controller;

import com.later.common.converter.IConverter;
import com.later.work.service.IComicChapterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comic_chapter")
public class ComicChapterController {

    private final IComicChapterService iComicChapterService;

    private final IConverter iConverter;

    ComicChapterController(final IComicChapterService iComicChapterService,
                    final IConverter iConverter) {
        this.iComicChapterService = iComicChapterService;
        this.iConverter = iConverter;
    }


}
