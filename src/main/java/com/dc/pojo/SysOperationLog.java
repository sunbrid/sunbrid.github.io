package com.dc.pojo;

import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class SysOperationLog implements Serializable {
    @ApiModelProperty(value = "")
    private int operationlog_id;
    @ApiModelProperty(value = " 操作人id")
    private int user_id;
    @ApiModelProperty(value = " 操作人姓名")
    private String user_name;
    @ApiModelProperty(value = " 操作人账号")
    private String user_account;
    @ApiModelProperty(value = " 操作时间")
    private Date date;
    @ApiModelProperty(value = " 操作内容")
    private String content;
    @ApiModelProperty(value = " 操作类型")
    private String type;
    @ApiModelProperty(value = " 操作模块")
    private String module;
    @ApiModelProperty(value = " 请求ip ")
    private String request_ip;
    @ApiModelProperty(value = " 请求前数据  ")
    private String param_before;
    @ApiModelProperty(value = " 请求后数据")
    private String param_after;
    @ApiModelProperty(value = " 请求参数")
    private String request_params;
    @ApiModelProperty(value = " 请求方法")
    private String request_method;

    public int getOperationlog_id() {
        return operationlog_id;
    }

    public void setOperationlog_id(int operationlog_id) {
        this.operationlog_id = operationlog_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getRequest_ip() {
        return request_ip;
    }

    public void setRequest_ip(String request_ip) {
        this.request_ip = request_ip;
    }

    public String getParam_before() {
        return param_before;
    }

    public void setParam_before(String param_before) {
        this.param_before = param_before;
    }

    public String getParam_after() {
        return param_after;
    }

    public void setParam_after(String param_after) {
        this.param_after = param_after;
    }

    public String getRequest_params() {
        return request_params;
    }

    public void setRequest_params(String request_params) {
        this.request_params = request_params;
    }

    public String getRequest_method() {
        return request_method;
    }

    public void setRequest_method(String request_method) {
        this.request_method = request_method;
    }

}