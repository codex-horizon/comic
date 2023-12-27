package com.later.work.controller;

import com.later.common.converter.IConverter;
import com.later.work.service.IComicService;
import org.springframework.web.bind.annotation.*;

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


}
