package com.later.work.dto;

import com.later.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MenuDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotNull(message = "id 空", groups = {
            GroupValidator.Modify.class
    })
    private Long id;

    @NotBlank(message = "name 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String name;

    @NotBlank(message = "path 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String path;

    @NotBlank(message = "icon 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String icon;

    @NotBlank(message = "sort 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String sort;
}
