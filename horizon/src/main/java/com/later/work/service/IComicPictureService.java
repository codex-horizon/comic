package com.later.work.service;

import com.later.work.bo.ComicPictureBo;

public interface IComicPictureService {

    void uploadSendUpdate(ComicPictureBo comicPictureBo);

    String recover(Long id);

    void rollBackRecord(ComicPictureBo comicPictureBo);

    ComicPictureBo fetchById(Long id);

    void save(ComicPictureBo comicPictureBo);
}
