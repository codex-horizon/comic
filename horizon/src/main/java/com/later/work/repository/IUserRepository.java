package com.later.work.repository;

import com.later.core.repository.jpa.IJpaRepository;
import com.later.work.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends IJpaRepository<UserEntity, Long> {
}
