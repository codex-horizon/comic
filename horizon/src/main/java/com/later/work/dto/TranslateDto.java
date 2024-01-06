package com.later.work.dto;

import com.later.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class TranslateDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotEmpty(message = "text 空", groups = {
            GroupValidator.Translate.class
    })
    private String text;
}
