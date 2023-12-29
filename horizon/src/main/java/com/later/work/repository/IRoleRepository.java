package com.later.work.repository;

import com.later.core.repository.jpa.IJpaRepository;
import com.later.work.entity.RoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends IJpaRepository<RoleEntity, Long> {
}
