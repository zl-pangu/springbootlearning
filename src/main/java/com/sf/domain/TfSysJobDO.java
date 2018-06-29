package com.sf.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 一次性任务表：执行完立即删除
 * 
 * @author wangxiaoshuai
 * @email happyggq@163.com
 * @date 2018-06-26 11:11:37
 */
public class TfSysJobDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//任务ID
	private String jobId;
	//任务名称
	private String jobName;
	//任务类型：1-普通任务
	private Integer jobType;
	//任务执行类
	private String jobClass;
	//任务参数：标准JSON格式
	private String jobParams;
	//告警方式： 1-邮件
	private Integer alarmType;
	//告警接受对象
	private String alarmReceiver;
	//有效标识：0-禁用，1-启用
	private Integer useTag;
	//任务执行时间：yyyyMMddHHmmss
	private Long execTime;
	//备注
	private String remark;
	//任务信息的创建时间
	private Date createTime;
	//任务信息的创建人ID
	private String createBy;
	//任务信息的更新时间：注意不是执行结果的更新时间
	private Date updateTime;
	//任务信息的更新人ID
	private String updateBy;
	//所在服务节点
	private String serverNode;

	/**
	 * 设置：任务ID
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	/**
	 * 获取：任务ID
	 */
	public String getJobId() {
		return jobId;
	}
	/**
	 * 设置：任务名称
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * 获取：任务名称
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * 设置：任务类型：1-普通任务
	 */
	public void setJobType(Integer jobType) {
		this.jobType = jobType;
	}
	/**
	 * 获取：任务类型：1-普通任务
	 */
	public Integer getJobType() {
		return jobType;
	}
	/**
	 * 设置：任务执行类
	 */
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	/**
	 * 获取：任务执行类
	 */
	public String getJobClass() {
		return jobClass;
	}
	/**
	 * 设置：任务参数：标准JSON格式
	 */
	public void setJobParams(String jobParams) {
		this.jobParams = jobParams;
	}
	/**
	 * 获取：任务参数：标准JSON格式
	 */
	public String getJobParams() {
		return jobParams;
	}
	/**
	 * 设置：告警方式： 1-邮件
	 */
	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
	/**
	 * 获取：告警方式： 1-邮件
	 */
	public Integer getAlarmType() {
		return alarmType;
	}
	/**
	 * 设置：告警接受对象
	 */
	public void setAlarmReceiver(String alarmReceiver) {
		this.alarmReceiver = alarmReceiver;
	}
	/**
	 * 获取：告警接受对象
	 */
	public String getAlarmReceiver() {
		return alarmReceiver;
	}
	/**
	 * 设置：有效标识：0-禁用，1-启用
	 */
	public void setUseTag(Integer useTag) {
		this.useTag = useTag;
	}
	/**
	 * 获取：有效标识：0-禁用，1-启用
	 */
	public Integer getUseTag() {
		return useTag;
	}
	/**
	 * 设置：任务执行时间：yyyyMMddHHmmss
	 */
	public void setExecTime(Long execTime) {
		this.execTime = execTime;
	}
	/**
	 * 获取：任务执行时间：yyyyMMddHHmmss
	 */
	public Long getExecTime() {
		return execTime;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：任务信息的创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：任务信息的创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：任务信息的创建人ID
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：任务信息的创建人ID
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：任务信息的更新时间：注意不是执行结果的更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：任务信息的更新时间：注意不是执行结果的更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：任务信息的更新人ID
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：任务信息的更新人ID
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：所在服务节点
	 */
	public void setServerNode(String serverNode) {
		this.serverNode = serverNode;
	}
	/**
	 * 获取：所在服务节点
	 */
	public String getServerNode() {
		return serverNode;
	}
}
