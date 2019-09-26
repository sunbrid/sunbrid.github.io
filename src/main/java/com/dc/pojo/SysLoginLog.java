package com.dc.pojo;

import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
/**
 * @title:<h3> 登录日志po类 <h3>
 * @author: Enzo
 * @date: 2018-11-12 10:40
 * @params
 * @return
 **/
public class SysLoginLog implements Serializable {
    @ApiModelProperty(value = "登录日志主键id")
    private int login_log_id;
    @ApiModelProperty(value = " 用户id")
    private int user_id;
    @ApiModelProperty(value = " 用户提交的账号")
    private String user_account;
    @ApiModelProperty(value = " 用户提交的密码")
    private String user_password;
    @ApiModelProperty(value = " 用户姓名")
    private String user_name;
    @ApiModelProperty(value = " 登陆时间 ")
    private Date login_date;
    @ApiModelProperty(value = " 登出时间")
    private Date loginout_date;
    @ApiModelProperty(value = " 请求的ip地址")
    private String request_ip;
    @ApiModelProperty(value = " 登陆状态[1成功，2帐号不存在，3密码错误，4帐号未激活,5帐号被锁定]")
    private int state;
    @ApiModelProperty(value = " 登陆类型[0pc]")
    private int type;
    @ApiModelProperty(value = " 设备imei号")
    private String imei;
    @ApiModelProperty(value = " 手机型号")
    private String model_number;
    @ApiModelProperty(value = " 版本号 ")
    private String version_num;
    @ApiModelProperty(value = " 登出类型[0,1注销]")
    private int loginout_type;
    @ApiModelProperty(value = " 会话sessionId")
    private String session_id;
	public int getLogin_log_id() {
		return login_log_id;
	}
	public void setLogin_log_id(int login_log_id) {
		this.login_log_id = login_log_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Date getLogin_date() {
		return login_date;
	}
	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}
	public Date getLoginout_date() {
		return loginout_date;
	}
	public void setLoginout_date(Date loginout_date) {
		this.loginout_date = loginout_date;
	}
	public String getRequest_ip() {
		return request_ip;
	}
	public void setRequest_ip(String request_ip) {
		this.request_ip = request_ip;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getModel_number() {
		return model_number;
	}
	public void setModel_number(String model_number) {
		this.model_number = model_number;
	}
	public String getVersion_num() {
		return version_num;
	}
	public void setVersion_num(String version_num) {
		this.version_num = version_num;
	}
	public int getLoginout_type() {
		return loginout_type;
	}
	public void setLoginout_type(int loginOut_type) {
		this.loginout_type = loginOut_type;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
    

    
}