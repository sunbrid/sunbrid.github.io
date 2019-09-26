package com.dc.pojo;

import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SysUserRole implements Serializable {

    @ApiModelProperty(value = "   用户角色自增id")
    private int user_role_id;

    @ApiModelProperty(value = "   用户id")
    private int fk_user_id;

    @ApiModelProperty(value = "   角色id")
    private int fk_role_id;

    public int getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(int user_role_id) {
        this.user_role_id = user_role_id;
    }

    public int getFk_user_id() {
        return fk_user_id;
    }

    public void setFk_user_id(int fk_user_id) {
        this.fk_user_id = fk_user_id;
    }

    public int getFk_role_id() {
        return fk_role_id;
    }

    public void setFk_role_id(int fk_role_id) {
        this.fk_role_id = fk_role_id;
    }


}