package com.later.work.service.impl;

import com.later.common.converter.IConverter;
import com.later.work.repository.IComicChapterRepository;
import com.later.work.service.IComicChapterService;
import org.springframework.stereotype.Service;

@Service
public class ComicChapterService implements IComicChapterService {

    private final IConverter iConverter;

    private final IComicChapterRepository iComicChapterRepository;

    ComicChapterService(final IConverter iConverter,
                        final IComicChapterRepository iComicChapterRepository){
        this.iConverter = iConverter;
        this.iComicChapterRepository = iComicChapterRepository;
    }

}
