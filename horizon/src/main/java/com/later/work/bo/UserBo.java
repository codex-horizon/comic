package com.later.work.bo;

import com.later.core.repository.entity.AbstractBoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserBo extends AbstractBoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String username;

    private String password;

    private Long roleId;

    private List<Long> comicIds;
}
