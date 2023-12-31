package com.later.work.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.later.common.converter.IConverter;
import com.later.common.helper.RequestHelper;
import com.later.common.helper.XTokenHelper;
import com.later.common.restful.IPageable;
import com.later.common.restful.PageableQry;
import com.later.work.entity.ComicEntity;
import com.later.work.entity.UserComicEntity;
import com.later.work.qry.ComicQry;
import com.later.work.repository.IComicRepository;
import com.later.work.repository.IUserComicRepository;
import com.later.work.service.IComicService;
import com.later.work.vo.ComicVo;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ComicService implements IComicService {

    private final IConverter iConverter;

    private final IComicRepository iComicRepository;

    private final IUserComicRepository iUserComicRepository;

    ComicService(final IConverter iConverter,
                 final IComicRepository iComicRepository,
                 final IUserComicRepository iUserComicRepository) {
        this.iConverter = iConverter;
        this.iComicRepository = iComicRepository;
        this.iUserComicRepository = iUserComicRepository;
    }

    @Override
    public IPageable<List<ComicVo>> pageable(ComicQry comicQry) {
        String xToken = RequestHelper.getHttpServletRequest().getParameter("xToken");
        Map<String, Claim> claims = XTokenHelper.getClaims(xToken, "123456");

        UserComicEntity userComicEntity = new UserComicEntity();
        userComicEntity.setUserId(claims.get("id").as(Long.class));
        List<UserComicEntity> userComicEntities = iUserComicRepository.findAll(Example.of(userComicEntity));
        List<Long> ids = userComicEntities.stream().map(UserComicEntity::getComicId).collect(Collectors.toList());

        Specification<ComicEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(comicQry.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + comicQry.getName() + "%"));
            }
            if (!CollectionUtils.isEmpty(ids)) {
                predicates.add(criteriaBuilder.in(root.get("id")).value(ids));
            }
            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<ComicEntity> comicEntities = iComicRepository.findAll(specification, PageRequest.of(
                comicQry.getCurrentIndex(),
                comicQry.getPageableSize(), Sort.Direction.DESC, "lastModifiedDate")
        );
        return IPageable.Pageable.response(comicEntities.getTotalElements(), iConverter.convert(comicEntities, ComicVo.class));
    }

    @Override
    public List<ComicVo> list(ComicQry comicQry) {
        Specification<ComicEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(comicQry.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + comicQry.getName() + "%"));
            }
            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        return iConverter.convert(iComicRepository.findAll(specification), ComicVo.class);
    }

}
