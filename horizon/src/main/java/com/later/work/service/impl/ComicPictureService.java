package com.later.work.service.impl;

import com.later.common.constants.Constants;
import com.later.common.converter.IConverter;
import com.later.common.exception.BizException;
import com.later.work.bo.ComicPictureBo;
import com.later.work.entity.ComicPictureEntity;
import com.later.work.repository.IComicPictureRepository;
import com.later.work.service.IComicPictureService;
import org.springframework.stereotype.Service;

@Service
public class ComicPictureService implements IComicPictureService {

    private final IComicPictureRepository iComicPictureRepository;

    private final IConverter iConverter;

    ComicPictureService(final IComicPictureRepository iComicPictureRepository,
                        final IConverter iConverter) {
        this.iComicPictureRepository = iComicPictureRepository;
        this.iConverter = iConverter;
    }

    @Override
    public void uploadSendUpdate(ComicPictureBo comicPictureBo) {
        ComicPictureEntity comicPictureEntity = iComicPictureRepository.findById(comicPictureBo.getId()).orElseThrow(() -> new BizException(Constants.BizStatus.Comic_Picture_Not_Exists));
        comicPictureEntity.setLastModifiedUrl(comicPictureBo.getLastModifiedUrl());
        iComicPictureRepository.save(comicPictureEntity);
    }

    @Override
    public String recover(Long id) {
        ComicPictureEntity comicPictureEntity = iComicPictureRepository.findById(id).orElseThrow(() -> new BizException(Constants.BizStatus.Comic_Picture_Not_Exists));
        comicPictureEntity.setLastModifiedUrl(comicPictureEntity.getUrl());
        return iComicPictureRepository.save(comicPictureEntity).getLastModifiedUrl();
    }

    @Override
    public void rollBackRecord(ComicPictureBo comicPictureBo) {
        ComicPictureEntity comicPictureEntity = iComicPictureRepository.findById(comicPictureBo.getId()).orElseThrow(() -> new BizException(Constants.BizStatus.Comic_Picture_Not_Exists));
        comicPictureEntity.setLastModifiedUrl(comicPictureBo.getLastModifiedUrl());
        iComicPictureRepository.save(comicPictureEntity);
    }

    @Override
    public ComicPictureBo fetchById(Long id) {
        ComicPictureEntity comicPictureEntity = iComicPictureRepository.findById(id).orElseThrow(() -> new BizException(Constants.BizStatus.Comic_Picture_Not_Exists));
        return iConverter.convert(comicPictureEntity, ComicPictureBo.class);
    }

    @Override
    public void save(ComicPictureBo comicPictureBo) {
        ComicPictureEntity comicPictureEntity = iComicPictureRepository.findById(comicPictureBo.getId()).orElseThrow(() -> new BizException(Constants.BizStatus.Comic_Picture_Not_Exists));
        comicPictureEntity.setRollBackRecord(comicPictureBo.getRollBackRecord());
        iComicPictureRepository.save(comicPictureEntity);
    }
}
