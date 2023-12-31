package com.later.work.service.impl;

import com.later.common.constants.Constants;
import com.later.common.converter.IConverter;
import com.later.common.exception.BizException;
import com.later.common.restful.IPageable;
import com.later.work.bo.UserBo;
import com.later.work.entity.*;
import com.later.work.qry.UserQry;
import com.later.work.repository.*;
import com.later.work.service.IUserService;
import com.later.work.vo.ComicVo;
import com.later.work.vo.RoleVo;
import com.later.work.vo.UserVo;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final IConverter iConverter;

    private final IUserRepository iUserRepository;

    private final IUserRoleRepository iUserRoleRepository;

    private final IRoleRepository iRoleRepository;

    private final IUserComicRepository iUserComicRepository;

    private final IComicRepository iComicRepository;

    UserService(final IConverter iConverter,
                final IUserRepository iUserRepository,
                final IUserRoleRepository iUserRoleRepository,
                final IRoleRepository iRoleRepository,
                final IUserComicRepository iUserComicRepository,
                final IComicRepository iComicRepository) {
        this.iConverter = iConverter;
        this.iUserRepository = iUserRepository;
        this.iUserRoleRepository = iUserRoleRepository;
        this.iRoleRepository = iRoleRepository;
        this.iUserComicRepository = iUserComicRepository;
        this.iComicRepository = iComicRepository;
    }

    @Override
    public IPageable<List<UserVo>> pageable(UserQry userQry) {
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
        List<UserVo> userVos = iConverter.convert(userEntities, UserVo.class);
        userVos.forEach(userVo -> {
            if (userVo.getUsername().equals("admin")) {
                userVo.setComics(iConverter.convert(iComicRepository.findAll(), ComicVo.class));

                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setName("管理员");
                Optional<RoleEntity> optionalRoleEntity = iRoleRepository.findOne(Example.of(roleEntity));
                optionalRoleEntity.ifPresent(entity -> userVo.setRole(iConverter.convert(entity, RoleVo.class)));
            } else {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setUserId(userVo.getId());
                Optional<UserRoleEntity> optionalUserRoleEntity = iUserRoleRepository.findOne(Example.of(userRoleEntity));
                if (optionalUserRoleEntity.isPresent()) {
                    Optional<RoleEntity> optionalRoleEntity = iRoleRepository.findById(optionalUserRoleEntity.get().getRoleId());
                    optionalRoleEntity.ifPresent(roleEntity -> userVo.setRole(iConverter.convert(roleEntity, RoleVo.class)));
                }

                UserComicEntity userComicEntity = new UserComicEntity();
                userComicEntity.setUserId(userVo.getId());
                List<UserComicEntity> userComicEntities = iUserComicRepository.findAll(Example.of(userComicEntity));
                List<ComicEntity> comicEntities = iComicRepository.findAllById(userComicEntities.stream().map(UserComicEntity::getComicId).collect(Collectors.toList()));
                userVo.setComics(iConverter.convert(comicEntities, ComicVo.class));
            }
        });
        return IPageable.Pageable.response(userEntities.getTotalElements(), userVos);
    }

    @Override
    public Long add(UserBo userBo) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userBo.getUsername());
        if (iUserRepository.exists(Example.of(userEntity))) {
            throw new BizException(Constants.BizStatus.User_Exists);
        }
        userBo.setState(Constants.DataState.Disabled.getState());
        Long id = iUserRepository.save(iConverter.convert(userBo, UserEntity.class)).getId();

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(id);
        userRoleEntity.setRoleId(userBo.getRoleId());
        userRoleEntity.setState(Constants.DataState.Disabled.getState());
        iUserRoleRepository.save(userRoleEntity);

        if (!CollectionUtils.isEmpty(userBo.getComicIds())) {
            List<UserComicEntity> userComicEntities = new ArrayList<>();
            UserComicEntity userComicEntity;
            for (Long comicId : userBo.getComicIds()) {
                userComicEntity = new UserComicEntity();
                userComicEntity.setUserId(id);
                userComicEntity.setComicId(comicId);
                userComicEntities.add(userComicEntity);
            }
            iUserComicRepository.saveAll(userComicEntities);
        }
        return id;
    }

    @Override
    public Long update(UserBo userBo) {
        UserEntity userEntity = iUserRepository.findById(userBo.getId()).orElseThrow(() -> new BizException(Constants.BizStatus.User_Exists));
        userEntity.setPassword(userBo.getPassword());
        Long id = iUserRepository.save(userEntity).getId();

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userBo.getId());
        Optional<UserRoleEntity> optionalUserRoleEntity = iUserRoleRepository.findOne(Example.of(userRoleEntity));
        optionalUserRoleEntity.ifPresent(iUserRoleRepository::delete);

        userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(id);
        userRoleEntity.setRoleId(userBo.getRoleId());
        userRoleEntity.setState(Constants.DataState.Disabled.getState());
        iUserRoleRepository.save(userRoleEntity);

        if (!CollectionUtils.isEmpty(userBo.getComicIds())) {
            List<UserComicEntity> userComicEntities = new ArrayList<>();
            UserComicEntity userComicEntity;
            for (Long comicId : userBo.getComicIds()) {
                userComicEntity = new UserComicEntity();
                userComicEntity.setUserId(id);
                userComicEntity.setComicId(comicId);
                userComicEntities.add(userComicEntity);
            }
            iUserComicRepository.saveAll(userComicEntities);
        }
        return id;
    }

    @Override
    public Object login(UserBo userBo) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userBo.getUsername());
        userEntity.setPassword(userBo.getPassword());
//        if(iUserRepository.exists(Example.of(userEntity))){
//        }
//        Long id = iUserRepository.findOne(Example.of(userEntity)).orElseThrow(() -> new BizException(Constants.BizStatus.User_Not_Exists)).getId();
//        throw new BizException(Constants.BizStatus.User_Not_Exists);
        return iUserRepository.exists(Example.of(userEntity));
    }

    @Override
    public Boolean delete(Long id) {
        iUserRepository.deleteById(id);
        return true;
    }


}
