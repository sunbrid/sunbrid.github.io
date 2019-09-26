package com.dc.dto;

import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-21 9:15
 */
public class SysUserDTO implements Serializable {
    @ApiModelProperty("序号")
    private int count;
    @ApiModelProperty("角色名称")
    private String role_names;
    @ApiModelProperty(value = " 用户账号")
    private String account;


    @ApiModelProperty(value = " 用户姓名")
    private String name;
    @ApiModelProperty(value = " 手机号码")
    private String phone;


    @ApiModelProperty(value = " 用户状态0禁用1启用")
    private String state_str;


    @ApiModelProperty(value = "家庭住址")
    private String address;// 家庭住址

    @ApiModelProperty(value = "邮箱")
    private String email;// 邮箱

    @ApiModelProperty(value = "出生日期")
    private String birthday_str;// 出生日期

    @ApiModelProperty(value = "性别")
    private String sex_str;
    @ApiModelProperty(value = "备注")
    private String memo;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState_str() {
        return state_str;
    }

    public void setState_str(String state_str) {
        this.state_str = state_str;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday_str() {
        return birthday_str;
    }

    public void setBirthday_str(String birthday_str) {
        this.birthday_str = birthday_str;
    }

    public String getSex_str() {
        return sex_str;
    }

    public void setSex_str(String sex_str) {
        this.sex_str = sex_str;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRole_names() {
        return role_names;
    }

    public void setRole_names(String role_names) {
        this.role_names = role_names;
    }
}
