package com.dc.pojo;

import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * @title:<h3> 用户po类 <h3>
 * @author: Enzo
 * @date: 2018-11-12 10:41
 * @params
 * @return
 **/
public class SysUser implements Serializable {
    @ApiModelProperty(value = " 用户id")
    private int user_id;

    @ApiModelProperty(value = " 用户账号")
    private String account;

    @ApiModelProperty(value = " 用户密码")
    private String password;

    @ApiModelProperty(value = " 用户姓名")
    private String name;
    @ApiModelProperty(value = " 手机号码")
    private String phone;
    @ApiModelProperty(value = " 头像地址")
    private String head_img_url;

    @ApiModelProperty(value = " 用户状态0禁用1启用")
    private int state;

    @ApiModelProperty(value = " 创建人用户id")
    private int create_user_id;

    @ApiModelProperty(value = " 创建人用户名")
    private String create_user_name;

    @ApiModelProperty(value = " 创建时间")
    private Date create_time;

    @ApiModelProperty(value = " 更新人用户id")
    private int update_user_id;

    @ApiModelProperty(value = " 更新人用户名")
    private String update_user_name;

    @ApiModelProperty(value = " 更新时间")
    private Date update_time;

    @ApiModelProperty(value = "是否删除，1标记已删除")
    private int is_deleted;

    @ApiModelProperty(value = "家庭住址")
    private String address;// 家庭住址

    @ApiModelProperty(value = "邮箱")
    private String email;// 邮箱

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;// 出生日期
    @ApiModelProperty(value = "乐观锁")
    private int version;
    @ApiModelProperty(value = "性别")
    private int sex;
    @ApiModelProperty(value = "备注")
    private String memo;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getHead_img_url() {
        return head_img_url;
    }

    public void setHead_img_url(String head_img_url) {
        this.head_img_url = head_img_url;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(int create_user_id) {
        this.create_user_id = create_user_id;
    }

    public String getCreate_user_name() {
        return create_user_name;
    }

    public void setCreate_user_name(String create_user_name) {
        this.create_user_name = create_user_name;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_user_id() {
        return update_user_id;
    }

    public void setUpdate_user_id(int update_user_id) {
        this.update_user_id = update_user_id;
    }

    public String getUpdate_user_name() {
        return update_user_name;
    }

    public void setUpdate_user_name(String update_user_name) {
        this.update_user_name = update_user_name;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
