package com.later.work.vo;

import com.later.core.repository.entity.AbstractVoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVo extends AbstractVoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String comicId;

    private Integer reco;

    private String name;

    private String alias;

    private Integer numberName;

    private String pic;

    private String picx;

    private Integer cid;

    private Integer tid;

    private String serialize;

    private String author;

    private String tags;

    private String content;

    private Integer hits;

    private Integer yhits;

    private Integer zhits;

    private Integer rhits;

    private Integer shits;

    private Integer dhits;

    private Integer cion;

    private Integer pay;

    private Integer nums;

    private BigDecimal score;

    private String zyid;

    private Integer type;

    private Integer up;

    private Integer showNum;

    private Integer readTime;

    private Integer newSort;

    private Integer hotSort;

    private Integer serialSort;

    private Integer endSort;

    private Integer gender;

    private Integer exist;

    private Integer collection;

    private Integer hide;

    private Integer hideType;

    private Integer like;

    private String pushName;

    private String pushContent;

    private Timestamp ctime;

    private Timestamp uptime;

    private Integer gl;

    private Integer addtime;

}
