package com.later.work.service.impl;

import com.later.common.constants.Constants;
import com.later.common.converter.IConverter;
import com.later.common.exception.BizException;
import com.later.common.restful.IPageable;
import com.later.work.bo.RoleBo;
import com.later.work.entity.MenuEntity;
import com.later.work.entity.RoleEntity;
import com.later.work.entity.RoleMenuEntity;
import com.later.work.qry.RoleQry;
import com.later.work.repository.IMenuRepository;
import com.later.work.repository.IRoleMenuRepository;
import com.later.work.repository.IRoleRepository;
import com.later.work.service.IRoleService;
import com.later.work.vo.MenuVo;
import com.later.work.vo.RoleVo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    private final IConverter iConverter;

    private final IRoleRepository iRoleRepository;

    private final IRoleMenuRepository iRoleMenuRepository;

    private final IMenuRepository iMenuRepository;

    RoleService(final IConverter iConverter,
                final IRoleRepository iRoleRepository,
                final IRoleMenuRepository iRoleMenuRepository,
                final IMenuRepository iMenuRepository) {
        this.iConverter = iConverter;
        this.iRoleRepository = iRoleRepository;
        this.iRoleMenuRepository = iRoleMenuRepository;
        this.iMenuRepository = iMenuRepository;
    }

    @Override
    public IPageable<List<RoleVo>> pageable(RoleQry roleQry) {
        Specification<RoleEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(roleQry.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + roleQry.getName() + "%"));
            }
            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<RoleEntity> roleEntities = iRoleRepository.findAll(specification, PageRequest.of(
                roleQry.getCurrentIndex(),
                roleQry.getPageableSize(),
                Sort.Direction.DESC,
                "lastModifiedDate"
        ));
        RoleMenuEntity roleMenuEntity;
        List<RoleVo> roleVos = iConverter.convert(roleEntities, RoleVo.class);
        for (RoleVo roleVo : roleVos) {
            if (roleVo.getName().equals("管理员")) {
                roleVo.setMenus(iConverter.convert(iMenuRepository.findAll(), MenuVo.class));
            } else {
                roleMenuEntity = new RoleMenuEntity();
                roleMenuEntity.setRoleId(roleVo.getId());
                List<RoleMenuEntity> roleMenuEntities = iRoleMenuRepository.findAll(Example.of(roleMenuEntity));
                List<MenuEntity> menuEntities = iMenuRepository.findAllById(roleMenuEntities.stream().map(RoleMenuEntity::getMenuId).collect(Collectors.toList()));
                roleVo.setMenus(iConverter.convert(menuEntities, MenuVo.class));
            }
        }
        return IPageable.Pageable.response(roleEntities.getTotalElements(), roleVos);
    }

    @Override
    public Long add(RoleBo roleBo) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleBo.getName());
        if (iRoleRepository.exists(Example.of(roleEntity))) {
            throw new BizException(Constants.BizStatus.Role_Exists);
        }
        roleBo.setState(Constants.DataState.Disabled.getState());
        Long id = iRoleRepository.save(iConverter.convert(roleBo, RoleEntity.class)).getId();
        if (!CollectionUtils.isEmpty(roleBo.getMenuIds())) {
            List<RoleMenuEntity> roleMenuEntities = new ArrayList<>();
            RoleMenuEntity roleMenuEntity;
            for (Long menuId : roleBo.getMenuIds()) {
                roleMenuEntity = new RoleMenuEntity();
                roleMenuEntity.setRoleId(id);
                roleMenuEntity.setMenuId(menuId);
                roleMenuEntity.setState(Constants.DataState.Disabled.getState());
                roleMenuEntities.add(roleMenuEntity);
            }
            iRoleMenuRepository.saveAll(roleMenuEntities);
        }
        return id;
    }

    @Override
    public Long update(RoleBo roleBo) {
        RoleEntity roleEntity = iRoleRepository.findById(roleBo.getId()).orElseThrow(() -> new BizException(Constants.BizStatus.Role_Exists));
        roleEntity.setName(roleBo.getName());
        Long id = iRoleRepository.save(roleEntity).getId();
        if (!CollectionUtils.isEmpty(roleBo.getMenuIds())) {
            List<RoleMenuEntity> roleMenuEntities = new ArrayList<>();
            RoleMenuEntity roleMenuEntity;
            for (Long menuId : roleBo.getMenuIds()) {
                roleMenuEntity = new RoleMenuEntity();
                roleMenuEntity.setRoleId(id);
                roleMenuEntity.setMenuId(menuId);
                roleMenuEntity.setState(Constants.DataState.Disabled.getState());
                roleMenuEntities.add(roleMenuEntity);
            }
            iRoleMenuRepository.saveAll(roleMenuEntities);
        }
        return id;
    }

    @Override
    public List<RoleVo> list(RoleQry roleQry) {
        Specification<RoleEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(roleQry.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + roleQry.getName() + "%"));
            }
            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        return iConverter.convert(iRoleRepository.findAll(specification), RoleVo.class);
    }

    @Override
    public Boolean delete(Long id) {
        iRoleRepository.deleteById(id);
        return true;
    }
}
