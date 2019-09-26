package com.dc.vo;

import com.dc.pojo.SysRole;
import com.dc.pojo.SysRolePermission;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.wordnik.swagger.annotations.ApiOperation;

import java.io.Serializable;
import java.util.List;

/**
 * @author Enzo
 * @Description 角色vo对象
 * @date 2018-11-14 15:33
 */
public class SysRoleVo implements Serializable {
    @ApiModelProperty("角色id")
    private int role_id;
    @ApiModelProperty("角色信息")
    private SysRole role;
    @ApiModelProperty("角色权限信息")
    private List<SysRolePermission> listRolePermission;

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public List<SysRolePermission> getListRolePermission() {
        return listRolePermission;
    }

    public void setListRolePermission(List<SysRolePermission> listRolePermission) {
        this.listRolePermission = listRolePermission;
    }
}
