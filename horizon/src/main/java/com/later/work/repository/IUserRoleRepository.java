package com.later.work.repository;

import com.later.core.repository.jpa.IJpaRepository;
import com.later.work.entity.UserRoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoleRepository extends IJpaRepository<UserRoleEntity, Long> {
}
