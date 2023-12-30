package com.later.work.dto;

import com.later.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class RoleDto  implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotNull(message = "id 空", groups = {
            GroupValidator.Modify.class
    })
    private Long id;

    @NotBlank(message = "name 空", groups = {
            GroupValidator.Create.class
    })
    private String name;

    private List<Long> menuIds;

}
