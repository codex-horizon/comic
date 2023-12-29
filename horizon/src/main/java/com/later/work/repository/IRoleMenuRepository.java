package com.later.work.repository;

import com.later.core.repository.jpa.IJpaRepository;
import com.later.work.entity.RoleMenuEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleMenuRepository extends IJpaRepository<RoleMenuEntity, Long> {
}
