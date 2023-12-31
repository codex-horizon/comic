package com.later.work.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.later.common.converter.IConverter;
import com.later.common.helper.RequestHelper;
import com.later.common.helper.XTokenHelper;
import com.later.common.restful.IPageable;
import com.later.work.entity.ComicChapterEntity;
import com.later.work.entity.ComicEntity;
import com.later.work.entity.UserComicEntity;
import com.later.work.entity.UserEntity;
import com.later.work.qry.ComicQry;
import com.later.work.repository.IComicChapterRepository;
import com.later.work.repository.IComicRepository;
import com.later.work.repository.IUserComicRepository;
import com.later.work.repository.IUserRepository;
import com.later.work.service.IComicService;
import com.later.work.vo.ComicChapterVo;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComicService implements IComicService {

    private final IConverter iConverter;

    private final IComicRepository iComicRepository;

    private final IUserComicRepository iUserComicRepository;

    private IComicChapterRepository iComicChapterRepository;

    private IUserRepository iUserRepository;

    ComicService(final IConverter iConverter,
                 final IComicRepository iComicRepository,
                 final IUserComicRepository iUserComicRepository,
                 final IComicChapterRepository iComicChapterRepository,
                 final IUserRepository iUserRepository) {
        this.iConverter = iConverter;
        this.iComicRepository = iComicRepository;
        this.iUserComicRepository = iUserComicRepository;
        this.iComicChapterRepository = iComicChapterRepository;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public IPageable<List<ComicVo>> pageable(ComicQry comicQry) {
        String xToken = RequestHelper.getHttpServletRequest().getParameter("xToken");
        Map<String, Claim> claims = XTokenHelper.getClaims(xToken, "123456");
        Long id = claims.get("id").as(Long.class);
        Optional<UserEntity> optionalUserEntity = iUserRepository.findById(id);

        List<Long> ids = new ArrayList<>();
        if (optionalUserEntity.isPresent() && !optionalUserEntity.get().getUsername().equals("admin")) {
            UserComicEntity userComicEntity = new UserComicEntity();
            userComicEntity.setUserId(id);
            List<UserComicEntity> userComicEntities = iUserComicRepository.findAll(Example.of(userComicEntity));
            ids.addAll(userComicEntities.stream().map(UserComicEntity::getComicId).collect(Collectors.toList()));
        }

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
        List<ComicVo> comicVos = iConverter.convert(comicEntities, ComicVo.class);
        comicVos.forEach(comicVo -> {
            ComicChapterEntity comicChapterEntity = new ComicChapterEntity();
            comicChapterEntity.setMid(comicVo.getId());
            List<ComicChapterEntity> comicChapterEntities = iComicChapterRepository.findAll(Example.of(comicChapterEntity));
            comicVo.setComicChapters(iConverter.convert(comicChapterEntities, ComicChapterVo.class));
        });
        return IPageable.Pageable.response(comicEntities.getTotalElements(), comicVos);
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
