package com.dc.pojo;

import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
/**
 * @title:<h3> 角色po类 <h3>
 * @author: Enzo
 * @date: 2018-11-12 10:40
 * @params
 * @return
 **/
public class SysRole implements Serializable {
    @ApiModelProperty(value = "角色自增id")
    private int role_id;
    @ApiModelProperty(value = "角色描述")
    private String memo;

    @ApiModelProperty(value = "角色名称",required = true)
    private String name;


    @ApiModelProperty(value = "   创建人用户id")
    private int create_user_id;

    @ApiModelProperty(value = "   创建人用户名")
    private String create_user_name;

    @ApiModelProperty(value = "   创建时间")
    private Date create_time;

    @ApiModelProperty(value = "   更新人用户id")
    private int update_user_id;

    @ApiModelProperty(value = "   更新人用户名")
    private String update_user_name;

    @ApiModelProperty(value = "   更新时间")
    private Date update_time;

    @ApiModelProperty(value = "   1删除0存在")
    private int is_deleted;

    @ApiModelProperty(value = " 版本号")
    private int version;

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(Integer create_user_id) {
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

    public Integer getUpdate_user_id() {
        return update_user_id;
    }

    public void setUpdate_user_id(Integer update_user_id) {
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

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysRole{");
        sb.append("role_id=").append(role_id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", create_user_id=").append(create_user_id);
        sb.append(", create_user_name='").append(create_user_name).append('\'');
        sb.append(", create_time=").append(create_time);
        sb.append(", update_user_id=").append(update_user_id);
        sb.append(", update_user_name='").append(update_user_name).append('\'');
        sb.append(", update_time=").append(update_time);
        sb.append(", is_deleted=").append(is_deleted);
        sb.append(", version=").append(version);
        sb.append(", memo='").append(memo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
