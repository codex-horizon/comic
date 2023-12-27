package com.later.work.service.impl;

import com.later.common.converter.IConverter;
import com.later.work.repository.IComicPictureRepository;
import com.later.work.service.IComicPictureService;
import org.springframework.stereotype.Service;

@Service
public class ComicPictureService implements IComicPictureService {

    private final IComicPictureRepository iComicPictureRepository;

    private final IConverter iConverter;

    ComicPictureService(final IComicPictureRepository iComicPictureRepository,
                        final IConverter iConverter){
        this.iComicPictureRepository = iComicPictureRepository;
        this.iConverter = iConverter;
    }
}
