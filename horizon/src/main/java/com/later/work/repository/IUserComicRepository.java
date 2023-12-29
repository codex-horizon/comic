package com.later.work.repository;

import com.later.core.repository.jpa.IJpaRepository;
import com.later.work.entity.UserComicEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserComicRepository extends IJpaRepository<UserComicEntity, Long> {
}
