package com.later.work.service.impl;

import com.later.common.constants.Constants;
import com.later.common.converter.IConverter;
import com.later.common.exception.BizException;
import com.later.common.restful.IPageable;
import com.later.work.bo.RoleBo;
import com.later.work.entity.RoleEntity;
import com.later.work.qry.RoleQry;
import com.later.work.repository.IRoleRepository;
import com.later.work.service.IRoleService;
import com.later.work.vo.RoleVo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements IRoleService {

    private final IConverter iConverter;

    private final IRoleRepository iRoleRepository;

    RoleService(final IConverter iConverter,
                final IRoleRepository iRoleRepository) {
        this.iConverter = iConverter;
        this.iRoleRepository = iRoleRepository;
    }

    @Override
    public IPageable<List<RoleVo>> list(RoleQry roleQry) {
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
        return IPageable.Pageable.response(roleEntities.getTotalElements(), iConverter.convert(roleEntities, RoleVo.class));
    }

    @Override
    public Long add(RoleBo roleBo) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleBo.getName());
        if (iRoleRepository.exists(Example.of(roleEntity))) {
            throw new BizException(Constants.BizStatus.Role_Exists);
        }
        return iRoleRepository.save(iConverter.convert(roleBo, RoleEntity.class)).getId();
    }

    @Override
    public Long update(RoleBo roleBo) {
        RoleEntity roleEntity = iRoleRepository.findById(roleBo.getId()).orElseThrow(() -> new BizException(Constants.BizStatus.Role_Exists));
        roleEntity.setName(roleBo.getName());
        return iRoleRepository.save(roleEntity).getId();
    }
}
