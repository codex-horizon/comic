package com.later.work.service.impl;

import com.later.common.constants.Constants;
import com.later.common.converter.IConverter;
import com.later.common.exception.BizException;
import com.later.common.restful.IPageable;
import com.later.work.bo.MenuBo;
import com.later.work.entity.MenuEntity;
import com.later.work.qry.MenuQry;
import com.later.work.repository.IMenuRepository;
import com.later.work.service.IMenuService;
import com.later.work.vo.MenuVo;
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
public class MenuService implements IMenuService {

    private final IConverter iConverter;

    private final IMenuRepository iMenuRepository;

    MenuService(final IConverter iConverter,
                final IMenuRepository iMenuRepository) {
        this.iConverter = iConverter;
        this.iMenuRepository = iMenuRepository;
    }

    @Override
    public Long update(MenuBo menuBo) {
        MenuEntity menuEntity = iMenuRepository.findById(menuBo.getId()).orElseThrow(() -> new BizException(Constants.BizStatus.Menu_Exists));
        menuEntity.setName(menuBo.getName());
        menuEntity.setPath(menuBo.getPath());
        return iMenuRepository.save(menuEntity).getId();
    }

    @Override
    public Long add(MenuBo menuBo) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setName(menuBo.getName());
        menuEntity.setPath(menuBo.getPath());
        if (iMenuRepository.exists(Example.of(menuEntity))) {
            throw new BizException(Constants.BizStatus.Role_Exists);
        }
        return iMenuRepository.save(iConverter.convert(menuBo, MenuEntity.class)).getId();
    }

    @Override
    public IPageable<List<MenuVo>> list(MenuQry menuQry) {
        Specification<MenuEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(menuQry.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + menuQry.getName() + "%"));
            } if (StringUtils.hasText(menuQry.getPath())) {
                predicates.add(criteriaBuilder.like(root.get("path"), "%" + menuQry.getPath() + "%"));
            }
            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<MenuEntity> menuEntities = iMenuRepository.findAll(specification, PageRequest.of(
                menuQry.getCurrentIndex(),
                menuQry.getPageableSize(),
                Sort.Direction.DESC,
                "lastModifiedDate"
        ));
        return IPageable.Pageable.response(menuEntities.getTotalElements(), iConverter.convert(menuEntities, MenuVo.class));
    }
}
