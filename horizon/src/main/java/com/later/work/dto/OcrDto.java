package com.later.work.dto;

import com.later.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class OcrDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotEmpty(message = "image 空", groups = {
            GroupValidator.Ocr.class
    })
    private String image;

}
