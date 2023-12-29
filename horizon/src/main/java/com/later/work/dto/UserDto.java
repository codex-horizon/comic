package com.later.work.dto;

import com.later.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotBlank(message = "id 空", groups = {
            GroupValidator.Modify.class
    })
    private Long id;

    @NotBlank(message = "username 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String username;

    @NotBlank(message = "username 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String password;
}
