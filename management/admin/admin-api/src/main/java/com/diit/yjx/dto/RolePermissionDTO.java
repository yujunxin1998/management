package com.diit.yjx.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 角色权限dto
 * @author yjxbz
 */
@Data
@Accessors(chain = true)
public class RolePermissionDTO {
    private Long roleId;
    private List<Long> permissionIds;
    private Long menuId;
}