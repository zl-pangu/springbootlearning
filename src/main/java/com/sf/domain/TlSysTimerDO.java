package com.sf.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 定时任务执行历史表
 * 
 * @author wangxiaoshuai
 * @email happyggq@163.com
 * @date 2018-06-26 11:25:33
 */
public class TlSysTimerDO implements Serializable {
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
	//时间表达式：cron 表达式
	private String cronExpression;
	//临时变量：用于存储过程中的一些数据
	private String tmpVariable;
	//过期标识：0-未过期，1-过期。一般由机器人调度修改
	private Integer expiredTag;
	//预警方式： 0-短信，1-邮件
	private Integer alarmType;
	//预警接受对象
	private String alarmReceiver;
	//有效标识：0-禁用，1-启用
	private Integer useTag;
	//有效期开始时间：yyyyMMddHHmmss
	private Long startTime;
	//有效期结束时间：yyyyMMddHHmmss
	private Long endTime;
	//备注
	private String remark;
	//上次执行时间
	private String lastExecTime;
	//上次执行结束时间
	private String lastFinishTime;
	//下次执行时间
	private String nextExecTime;
	//异常标识
	private Integer exceptionTag;
	//异常信息
	private String exceptionInfo;
	//上次执行结果信息执行结果编码
	private String resultCode;
	//上次执行结果信息
	private String resultInfo;
	//任务信息的创建时间
	private Date createTime;
	//任务信息的创建人
	private String createBy;
	//任务信息的更新时间：注意不是执行结果的更新时间
	private Date updateTime;
	//任务信息的更新人
	private String updateBy;
	//所在服务节点
	private String serverNode;
	//是否执行中0-未执行，1-执行中，2-执行完成
	private Integer executing;
	//创建时间
	private Date tCreateTime;

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
	 * 设置：时间表达式：cron 表达式
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	/**
	 * 获取：时间表达式：cron 表达式
	 */
	public String getCronExpression() {
		return cronExpression;
	}
	/**
	 * 设置：临时变量：用于存储过程中的一些数据
	 */
	public void setTmpVariable(String tmpVariable) {
		this.tmpVariable = tmpVariable;
	}
	/**
	 * 获取：临时变量：用于存储过程中的一些数据
	 */
	public String getTmpVariable() {
		return tmpVariable;
	}
	/**
	 * 设置：过期标识：0-未过期，1-过期。一般由机器人调度修改
	 */
	public void setExpiredTag(Integer expiredTag) {
		this.expiredTag = expiredTag;
	}
	/**
	 * 获取：过期标识：0-未过期，1-过期。一般由机器人调度修改
	 */
	public Integer getExpiredTag() {
		return expiredTag;
	}
	/**
	 * 设置：预警方式： 0-短信，1-邮件
	 */
	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
	/**
	 * 获取：预警方式： 0-短信，1-邮件
	 */
	public Integer getAlarmType() {
		return alarmType;
	}
	/**
	 * 设置：预警接受对象
	 */
	public void setAlarmReceiver(String alarmReceiver) {
		this.alarmReceiver = alarmReceiver;
	}
	/**
	 * 获取：预警接受对象
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
	 * 设置：有效期开始时间：yyyyMMddHHmmss
	 */
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：有效期开始时间：yyyyMMddHHmmss
	 */
	public Long getStartTime() {
		return startTime;
	}
	/**
	 * 设置：有效期结束时间：yyyyMMddHHmmss
	 */
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：有效期结束时间：yyyyMMddHHmmss
	 */
	public Long getEndTime() {
		return endTime;
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
	 * 设置：上次执行时间
	 */
	public void setLastExecTime(String lastExecTime) {
		this.lastExecTime = lastExecTime;
	}
	/**
	 * 获取：上次执行时间
	 */
	public String getLastExecTime() {
		return lastExecTime;
	}
	/**
	 * 设置：上次执行结束时间
	 */
	public void setLastFinishTime(String lastFinishTime) {
		this.lastFinishTime = lastFinishTime;
	}
	/**
	 * 获取：上次执行结束时间
	 */
	public String getLastFinishTime() {
		return lastFinishTime;
	}
	/**
	 * 设置：下次执行时间
	 */
	public void setNextExecTime(String nextExecTime) {
		this.nextExecTime = nextExecTime;
	}
	/**
	 * 获取：下次执行时间
	 */
	public String getNextExecTime() {
		return nextExecTime;
	}
	/**
	 * 设置：异常标识
	 */
	public void setExceptionTag(Integer exceptionTag) {
		this.exceptionTag = exceptionTag;
	}
	/**
	 * 获取：异常标识
	 */
	public Integer getExceptionTag() {
		return exceptionTag;
	}
	/**
	 * 设置：异常信息
	 */
	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
	/**
	 * 获取：异常信息
	 */
	public String getExceptionInfo() {
		return exceptionInfo;
	}
	/**
	 * 设置：上次执行结果信息执行结果编码
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	/**
	 * 获取：上次执行结果信息执行结果编码
	 */
	public String getResultCode() {
		return resultCode;
	}
	/**
	 * 设置：上次执行结果信息
	 */
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
	/**
	 * 获取：上次执行结果信息
	 */
	public String getResultInfo() {
		return resultInfo;
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
	 * 设置：任务信息的创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：任务信息的创建人
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
	 * 设置：任务信息的更新人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：任务信息的更新人
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
	/**
	 * 设置：是否执行中0-未执行，1-执行中，2-执行完成
	 */
	public void setExecuting(Integer executing) {
		this.executing = executing;
	}
	/**
	 * 获取：是否执行中0-未执行，1-执行中，2-执行完成
	 */
	public Integer getExecuting() {
		return executing;
	}
	/**
	 * 设置：创建时间
	 */
	public void setTCreateTime(Date tCreateTime) {
		this.tCreateTime = tCreateTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getTCreateTime() {
		return tCreateTime;
	}
}
