package com.later.work.vo;

import com.later.core.repository.entity.AbstractVoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class ComicChapterVo extends AbstractVoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private Long mid;

    private Integer xid;

    private String name;

    private String pic;

    private Integer pnum;

    private String zyid;

    private Integer addtime;

    private Integer up;

    private String type;

    private String taskStatus;

    private Timestamp ctime;

}
