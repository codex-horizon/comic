package com.later.work.vo;

import com.later.core.repository.entity.AbstractVoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleVo extends AbstractVoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String name;

    private List<MenuVo> menus;
}
