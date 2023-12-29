package com.later.work.entity;

import com.later.core.repository.entity.AbstractPoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@javax.persistence.Table(name = "menu")
@Table(appliesTo = "menu", comment = "菜单表")
public class MenuEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "path", nullable = false, columnDefinition = "varchar(256) comment '路由'")
    private String path;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(256) comment '名称'")
    private String name;

    @Column(name = "icon", nullable = false, columnDefinition = "varchar(256) comment '图标'")
    private String icon;

    @Column(name = "sort", nullable = false, columnDefinition = "varchar(256) comment '排序'")
    private Integer sort;

    @Column(name = "parent_id", nullable = false, columnDefinition = "varchar(256) comment '父节点'")
    private Long parentId;

}
