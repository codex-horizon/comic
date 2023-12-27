package com.later.work.repository;

import com.later.core.repository.jpa.IJpaRepository;
import com.later.work.entity.ComicEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IComicRepository extends IJpaRepository<ComicEntity, Long> {
}
