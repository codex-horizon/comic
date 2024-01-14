package com.later.work.dto;

import com.later.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OssRecoverDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotNull(message = "id 空", groups = {
            GroupValidator.Oss.class
    })
    private Long id;

}
