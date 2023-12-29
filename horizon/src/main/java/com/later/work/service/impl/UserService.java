package com.later.work.service.impl;

import com.later.common.constants.Constants;
import com.later.common.converter.IConverter;
import com.later.common.exception.BizException;
import com.later.common.restful.IPageable;
import com.later.work.bo.UserBo;
import com.later.work.entity.UserEntity;
import com.later.work.qry.UserQry;
import com.later.work.repository.IUserRepository;
import com.later.work.service.IUserService;
import com.later.work.vo.UserVo;
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
public class UserService implements IUserService {

    private final IConverter iConverter;

    private final IUserRepository iUserRepository;

    UserService(final IConverter iConverter,
                final IUserRepository iUserRepository) {
        this.iConverter = iConverter;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public IPageable<List<UserVo>> list(UserQry userQry) {
        Specification<UserEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(userQry.getUsername())) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + userQry.getUsername() + "%"));
            }
            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<UserEntity> userEntities = iUserRepository.findAll(specification, PageRequest.of(
                userQry.getCurrentIndex(),
                userQry.getPageableSize(),
                Sort.Direction.DESC,
                "lastModifiedDate"
        ));
        return IPageable.Pageable.response(userEntities.getTotalElements(), iConverter.convert(userEntities, UserVo.class));
    }

    @Override
    public Long add(UserBo userBo) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userBo.getUsername());
        if (iUserRepository.exists(Example.of(userEntity))) {
            throw new BizException(Constants.BizStatus.User_Exists);
        }
        return iUserRepository.save(iConverter.convert(userBo, UserEntity.class)).getId();
    }

    @Override
    public Long update(UserBo userBo) {
        UserEntity userEntity = iUserRepository.findById(userBo.getId()).orElseThrow(() -> new BizException(Constants.BizStatus.User_Exists));
        userEntity.setPassword(userBo.getPassword());
        return iUserRepository.save(userEntity).getId();
    }

}
