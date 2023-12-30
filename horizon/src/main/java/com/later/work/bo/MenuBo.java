package com.later.work.bo;

import com.later.core.repository.entity.AbstractBoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuBo extends AbstractBoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String path;

    private String name;

    private String icon;

    private Integer sort;

    private Long parentId;

}

