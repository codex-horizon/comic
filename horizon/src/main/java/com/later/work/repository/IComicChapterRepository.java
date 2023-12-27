package com.later.work.repository;

import com.later.core.repository.jpa.IJpaRepository;
import com.later.work.entity.ComicChapterEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IComicChapterRepository extends IJpaRepository<ComicChapterEntity, Long> {
}
