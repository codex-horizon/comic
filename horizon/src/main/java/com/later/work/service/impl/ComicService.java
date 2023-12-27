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
    public IPageable<List<ComicVo>> list(ComicQry comicQry) {
        Specification<ComicEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<PageableQry.SimpleConditions<?>> conditions = comicQry.getConditions();
            if (StringUtils.hasText(comicQry.getUsername())) {
                criteriaBuilder.equal(root.get("username"), comicQry.getUsername());
            }
            return criteriaBuilder.conjunction();
        };
        Page<ComicEntity> comicEntities = iComicRepository.findAll(specification, PageRequest.of(
                comicQry.getCurrentIndex(),
                comicQry.getPageableSize(),
                StringUtils.hasText(comicQry.getDirection()) ? Sort.Direction.fromString(comicQry.getDirection()) : Sort.Direction.DESC,
                CollectionUtils.isEmpty(comicQry.getProperties()) ? String.join(",", comicQry.getProperties()) : "lastModifiedDate"
        ));
        return IPageable.Pageable.response(comicEntities.getTotalElements(), iConverter.convert(comicEntities, ComicVo.class));
    }

}
