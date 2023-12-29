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
@javax.persistence.Table(name = "user_comic")
@Table(appliesTo = "user_comic", comment = "用户-漫画表")
public class UserComicEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint(20) comment '账户ID'")
    private Long userId;

    @Column(name = "comic_id", nullable = false, columnDefinition = "bigint(20) comment '漫画ID'")
    private Long comicId;

}
