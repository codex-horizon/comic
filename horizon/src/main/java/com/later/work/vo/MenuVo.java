package com.later.work.vo;

import com.later.core.repository.entity.AbstractVoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuVo extends AbstractVoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String path;

    private String name;

    private String icon;

    private Integer sort;

    private Long parentId;

}

