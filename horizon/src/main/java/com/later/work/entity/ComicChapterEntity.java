package com.later.work.entity;

import com.later.core.repository.entity.AbstractPoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@javax.persistence.Table(name = "comic_chapter")
@Table(appliesTo = "comic_chapter", comment = "漫画章节")
public class ComicChapterEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "mid", columnDefinition = "int(11) comment '漫画ID'")
    private Integer mid;

    @Column(name = "xid", columnDefinition = "int(11) comment '排序ID'")
    private Integer xid;

    @Column(name = "name", columnDefinition = "varchar(256) comment '标题'")
    private String name;

    @Column(name = "pic", columnDefinition = "varchar(256) comment '图片地址'")
    private String pic;

    @Column(name = "pnum", columnDefinition = "int(11) comment '图片数量'")
    private Integer pnum;

    @Column(name = "zyid", columnDefinition = "varchar(256) comment '资源站ID'")
    private String zyid;

    @Column(name = "addtime", columnDefinition = "int(11) comment '入库时间'")
    private Integer addtime;

    @Column(name = "up", columnDefinition = "tinyint(1) comment ''")
    private Integer up;

    @Column(name = "type", columnDefinition = "varchar(256) comment '来源'")
    private String type;

    @Column(name = "task_status", columnDefinition = "tinyint(1) comment '任务状态 0-无 1-处理中'")
    private String taskStatus;

    @Column(name = "ctime", columnDefinition = "datetime comment '源创建时间'")
    private Timestamp ctime;

}
