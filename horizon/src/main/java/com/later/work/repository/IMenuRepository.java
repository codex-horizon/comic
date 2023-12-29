package com.later.work.repository;

import com.later.core.repository.jpa.IJpaRepository;
import com.later.work.entity.MenuEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuRepository extends IJpaRepository<MenuEntity, Long> {
}
