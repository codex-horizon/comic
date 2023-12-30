package com.later.work.service.impl;

import com.later.common.converter.IConverter;
import com.later.common.restful.IPageable;
import com.later.common.restful.PageableQry;
import com.later.work.entity.ComicEntity;
import com.later.work.qry.ComicQry;
import com.later.work.repository.IComicRepository;
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

@Service
public class ComicService implements IComicService {

    private final IConverter iConverter;

    private final IComicRepository iComicRepository;

    ComicService(final IConverter iConverter,
                 final IComicRepository iComicRepository) {
        this.iConverter = iConverter;
        this.iComicRepository = iComicRepository;
    }

    @Override
    public IPageable<List<ComicVo>> pageable(ComicQry comicQry) {
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
