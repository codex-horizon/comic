package com.later.work.entity;

import com.later.core.repository.entity.AbstractPoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@javax.persistence.Table(name = "comic")
@Table(appliesTo = "comic", comment = "漫画表")
public class ComicEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "comic_id", columnDefinition = "varchar(256) comment '源漫画id'")
    private String comicId;

    @Column(name = "reco", columnDefinition = "tinyint(1) comment '轮播图：0否，1是'")
    private Integer reco;

    @Column(name = "name", columnDefinition = "varchar(256) comment '标题'")
    private String name;

    @Column(name = "alias", columnDefinition = "varchar(256) comment ''")
    private String alias;

    @Column(name = "number_name", columnDefinition = "int(11) comment '数字标题'")
    private Integer numberName;

    @Column(name = "pic", columnDefinition = "varchar(256) comment '竖版封面'")
    private String pic;

    @Column(name = "picx", columnDefinition = "varchar(256) comment '横版封面'")
    private String picx;

    @Column(name = "cid", columnDefinition = "int(11) comment '分类ID'")
    private Integer cid;

    @Column(name = "tid", columnDefinition = "tinyint(1) comment '1推荐，0未推'")
    private Integer tid;

    @Column(name = "serialize", columnDefinition = "varchar(256) comment '状态'")
    private String serialize;

    @Column(name = "author", columnDefinition = "varchar(256) comment '漫画作者'")
    private String author;

    @Column(name = "tags", columnDefinition = "varchar(256) comment 'TAGS标签'")
    private String tags;

    @Column(name = "content", columnDefinition = "text comment '介绍'")
    private String content;

    @Column(name = "hits", columnDefinition = "int(11) comment '总点击'")
    private Integer hits;

    @Column(name = "yhits", columnDefinition = "int(11) comment '月点击'")
    private Integer yhits;

    @Column(name = "zhits", columnDefinition = "int(11) comment '周点击'")
    private Integer zhits;

    @Column(name = "rhits", columnDefinition = "int(11) comment '日点击'")
    private Integer rhits;

    @Column(name = "shits", columnDefinition = "int(11) comment '收藏人气'")
    private Integer shits;

    @Column(name = "dhits", columnDefinition = "int(11) comment '被推荐次数'")
    private Integer dhits;

    @Column(name = "cion", columnDefinition = "int(11) comment '每章书币'")
    private Integer cion;

    @Column(name = "pay", columnDefinition = "tinyint(1) comment '是否收费：0免费，1VIP，2书币'")
    private Integer pay;

    @Column(name = "nums", columnDefinition = "int(11) comment '章节总数'")
    private Integer nums;

    @Column(name = "score", columnDefinition = "decimal(2,1) comment '总得分'")
    private BigDecimal score;

    @Column(name = "zyid", columnDefinition = "varchar(256) comment '采集资源ID'")
    private String zyid;

    @Column(name = "type", columnDefinition = "tinyint(1) comment '资源站标识 1-lezhinus 2-bomtoon'")
    private Integer type;

    @Column(name = "up", columnDefinition = "tinyint(1) comment '是否采集'")
    private Integer up;

    @Column(name = "show_num", columnDefinition = "int(11) comment '观看数'")
    private Integer showNum;

    @Column(name = "read_time", columnDefinition = "int(11) comment '观看时长'")
    private Integer readTime;

    @Column(name = "new_sort", columnDefinition = "int(11) comment '新漫速递排序'")
    private Integer newSort;

    @Column(name = "hot_sort", columnDefinition = "int(11) comment '热门排序'")
    private Integer hotSort;

    @Column(name = "serial_sort", columnDefinition = "int(11) comment '连载排序'")
    private Integer serialSort;

    @Column(name = "end_sort", columnDefinition = "int(11) comment '完结排序'")
    private Integer endSort;

    @Column(name = "gender", columnDefinition = "tinyint(1) comment '性别频道 1-女 2-男 3-通用'")
    private Integer gender;

    @Column(name = "is_exist", columnDefinition = "tinyint(1) comment '用于判断采集漫画是否存在'")
    private Integer exist;

    @Column(name = "is_collection", columnDefinition = "tinyint(1) comment '是否采集 0-否 1-是'")
    private Integer collection;

    @Column(name = "is_hide", columnDefinition = "tinyint(1) comment '是否隐藏 0-否 1-是'")
    private Integer hide;

    @Column(name = "hide_type", columnDefinition = "tinyint(1) comment '隐藏方式 1-隐藏前端、分类 2-隐藏前端、分类、搜索漫画'")
    private Integer hideType;

    @Column(name = "`like`", columnDefinition = "int(11) comment '原站点赞数'")
    private Integer like;

    @Column(name = "push_name", columnDefinition = "varchar(256) comment '发布名称'")
    private String pushName;

    @Column(name = "push_content", columnDefinition = "varchar(512) comment '发布内容'")
    private String pushContent;

    @Column(name = "ctime", columnDefinition = "timestamp default current_timestamp comment '漫画添加时间'")
    private Timestamp ctime;

    @Column(name = "uptime", columnDefinition = "timestamp default current_timestamp comment '漫画更新时间'")
    private Timestamp uptime;

    @Column(name = "is_gl", columnDefinition = "tinyint(1) comment '是否gl'")
    private Integer gl;

    @Column(name = "addtime", columnDefinition = "int(11) comment '入库时间'")
    private Integer addtime;

}
