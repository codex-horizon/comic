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
@javax.persistence.Table(name = "user_role")
@Table(appliesTo = "user_role", comment = "用户-角色表")
public class UserRoleEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "user_id", nullable = false, columnDefinition = "varchar(256) comment '用户ID'")
    private Long userId;

    @Column(name = "role_id", nullable = false, columnDefinition = "varchar(256) comment '角色ID'")
    private Long roleId;

}
