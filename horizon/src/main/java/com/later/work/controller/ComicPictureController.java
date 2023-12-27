package com.later.work.controller;

import com.later.common.converter.IConverter;
import com.later.work.service.IComicPictureService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comic_picture")
public class ComicPictureController {

    private final IComicPictureService iComicPictureService;

    private final IConverter iConverter;

    ComicPictureController(final IComicPictureService iComicPictureService,
                           final IConverter iConverter){
        this.iComicPictureService = iComicPictureService;
        this.iConverter = iConverter;
    }
}
