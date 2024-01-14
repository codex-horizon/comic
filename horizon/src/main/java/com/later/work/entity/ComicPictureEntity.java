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
@javax.persistence.Table(name = "comic_pic")
@Table(appliesTo = "comic_pic", comment = "章节图片")
public class ComicPictureEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "mid", columnDefinition = "int(11) comment '漫画ID'")
    private Long mid;

    @Column(name = "zid", columnDefinition = "int(11) comment '章节ID'")
    private Long zid;

    @Column(name = "url", columnDefinition = "varchar(256) comment '图片url地址'")
    private String url;

    @Column(name = "last_modified_url", columnDefinition = "varchar(256) comment 'last_modified_url图片url地址'")
    private String lastModifiedUrl;

    @Column(name = "roll_back_record", columnDefinition = "int(11) comment '回滚记录图片url地址'")
    private Integer rollBackRecord;

    @Column(name = "ypic", columnDefinition = "varchar(256) comment '源站地址'")
    private String ypic;

    @Column(name = "height", columnDefinition = "int(11) comment '高'")
    private Integer height;

    @Column(name = "xid", columnDefinition = "int(11) comment '排序ID'")
    private Integer xid;

}