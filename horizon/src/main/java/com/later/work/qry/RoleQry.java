package com.later.work.qry;

import com.later.common.restful.PageableQry;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQry extends PageableQry {

    private String name;

}
