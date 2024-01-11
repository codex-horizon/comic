package com.later.work.dto;

import com.later.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class OssDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotEmpty(message = "image 空", groups = {
            GroupValidator.Oss.class
    })
    private String image;

    @NotEmpty(message = "uri 空", groups = {
            GroupValidator.Oss.class
    })
    private String uri;

}
