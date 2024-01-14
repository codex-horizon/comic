package com.later.work.dto;

import com.later.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OssRollBackRecordDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotNull(message = "id 空", groups = {
            GroupValidator.Oss.class
    })
    private Long id;

    @NotEmpty(message = "image 空", groups = {
            GroupValidator.Oss.class
    })
    private String image;

    @NotEmpty(message = "uri 空", groups = {
            GroupValidator.Oss.class
    })
    private String uri;

    private boolean hasRollBackRecord;

}
