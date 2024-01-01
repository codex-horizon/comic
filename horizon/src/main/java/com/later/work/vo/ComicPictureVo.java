package com.later.work.vo;

import com.later.core.repository.entity.AbstractVoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ComicPictureVo extends AbstractVoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private Long mid;

    private Long zid;

    private String url;

    private String ypic;

    private Integer height;

    private Integer xid;

}
