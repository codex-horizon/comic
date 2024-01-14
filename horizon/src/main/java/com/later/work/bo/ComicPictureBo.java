package com.later.work.bo;

import com.later.core.repository.entity.AbstractBoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ComicPictureBo extends AbstractBoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private Long mid;

    private Long zid;

    private String url;

    private String lastModifiedUrl;

    private int rollBackRecord;

    private String ypic;

    private Integer height;

    private Integer xid;

}