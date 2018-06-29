package com.thread.domain;

import java.io.Serializable;


/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-06-20 11:55:28
 */
public class CustUgDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//上传文件ID
	private Integer uid;
	//目标文件ID
	private Integer gid;
	//目标文件，导入文件关联字段
	private String flag;
	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：上传文件ID
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/**
	 * 获取：上传文件ID
	 */
	public Integer getUid() {
		return uid;
	}
	/**
	 * 设置：目标文件ID
	 */
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	/**
	 * 获取：目标文件ID
	 */
	public Integer getGid() {
		return gid;
	}
	/**
	 * 设置：目标文件，导入文件关联字段
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * 获取：目标文件，导入文件关联字段
	 */
	public String getFlag() {
		return flag;
	}
}
