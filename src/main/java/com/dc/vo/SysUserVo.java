package com.dc.vo;

import com.dc.pojo.SysFileLog;
import com.dc.pojo.SysRole;
import com.dc.pojo.SysUser;
import com.dc.pojo.SysUserRole;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Enzo
 * @Description 用户vo对象
 * @date 2018-11-15 16:23
 */
public class SysUserVo {
    @ApiModelProperty("用户id")
    private int user_id;
    @ApiModelProperty("角色名称")
    private String role_names;
    @ApiModelProperty("修改密码时存储旧密码")
    private String old_password;
    @ApiModelProperty("用户对象")
    private SysUser user;
    @ApiModelProperty("用户角色关系对象")
    private List<SysUserRole> listUserRole;
    @ApiModelProperty("角色信息")
    private List<SysRole> listRole;
    @ApiModelProperty("头像文件信息")
    private SysFileLog fileLog;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRole_names() {
        return role_names;
    }

    public void setRole_names(String role_names) {
        this.role_names = role_names;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public List<SysRole> getListRole() {
        return listRole;
    }

    public void setListRole(List<SysRole> listRole) {
        this.listRole = listRole;
    }

    public SysFileLog getFileLog() {
        return fileLog;
    }

    public void setFileLog(SysFileLog fileLog) {
        this.fileLog = fileLog;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public List<SysUserRole> getListUserRole() {
        return listUserRole;
    }

    public void setListUserRole(List<SysUserRole> listUserRole) {
        this.listUserRole = listUserRole;
    }
}
