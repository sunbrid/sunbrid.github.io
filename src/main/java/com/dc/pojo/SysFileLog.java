package com.dc.pojo;

import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <h2>文件上传记录</h2>
 * 
 * @ClassName: SysFileLog
 * @author Enzo
 * @date 2018年1月23日 上午11:24:10
 */
public class SysFileLog implements Serializable {
	@ApiModelProperty(value = "主键id")

	String file_log_id;
	@ApiModelProperty(value = " 原始文件名")
	String file_name;// 原始文件名
	@ApiModelProperty(value = "重命名的文件名")
	String file_rename;// 重命名的文件名
	@ApiModelProperty(value = "文件类型，不包含点")
	String file_type;// 文件类型，不包含点(doc)
	@ApiModelProperty(value = "文件保存地址")
	String save_path;// 文件保存地址(2017-01-01/default/2017010115300001_test.doc)
	@ApiModelProperty(value = "文件大小")
	long file_size;// 文件大小
	@ApiModelProperty(value = "文件长度，针对doc、pdf、xls文档的文件页码数,针对视频文件的时长")
	String file_length;// 文件长度，针对doc、pdf、xls文档的文件页码数,针对视频文件的时长(10)
	@ApiModelProperty(value = "转化状态，0不转化,1待转化 ，2转化中，3转化完成，4转化异常,")
	int state;// 转化状态，0不转化,1待转化 ，2转化中，3转化完成，4转化异常,
	@ApiModelProperty(value = "消息")
	String message = "";// 消息
	@ApiModelProperty(value = "上传时间")
	Date upload_time;// 上传时间
	@ApiModelProperty(value = "上传者")
	String uploader;// 上传者
	@ApiModelProperty(value = "是否删除")
	int isdeleted;

	public String getFile_log_id() {
		return file_log_id;
	}

	public void setFile_log_id(String file_log_id) {
		this.file_log_id = file_log_id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_rename() {
		return file_rename;
	}

	public void setFile_rename(String file_rename) {
		this.file_rename = file_rename;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getSave_path() {
		return save_path;
	}

	public void setSave_path(String save_path) {
		this.save_path = save_path;
	}

	public long getFile_size() {
		return file_size;
	}

	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}

	public String getFile_length() {
		return file_length;
	}

	public void setFile_length(String file_length) {
		this.file_length = file_length;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(Date upload_time) {
		this.upload_time = upload_time;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public int getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

}
