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
@javax.persistence.Table(name = "role_menu")
@Table(appliesTo = "role_menu", comment = "角色-菜单表")
public class RoleMenuEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "role_id", nullable = false, columnDefinition = "varchar(256) comment '角色名称ID'")
    private Long roleId;

    @Column(name = "menu_id", nullable = false, columnDefinition = "varchar(256) comment '菜单名称ID'")
    private Long menuId;

}
