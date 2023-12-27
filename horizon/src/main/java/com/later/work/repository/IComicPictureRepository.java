package com.later.work.repository;

import com.later.core.repository.jpa.IJpaRepository;
import com.later.work.entity.ComicPictureEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IComicPictureRepository extends IJpaRepository<ComicPictureEntity, Long> {
}