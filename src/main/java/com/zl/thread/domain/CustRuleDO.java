package com.zl.thread.domain;

import java.io.Serializable;


/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-06-20 13:40:10
 */
public class CustRuleDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//目标文件的列名
	private String targetColumn;
	//
	private String tableA;
	//第一个值
	private String columnA;
	//(加1，减2，乘3，除4，替换5)
	private String calculation;
	//
	private String tableB;
	//第二个值
	private String columnB;
	//(加1，减2，乘3，除4，替换5)
	private String calculations;
	//
	private String tableC;
	//第三个值
	private String columnC;
	//随机数
	private String flag;
	//第一个表的基准列
	private String benchmarkA;
	//第二个表的基准列
	private String benchmarkB;
	//第三个表的基准列
	private String benchmarkC;
	//默认值
	private String defaultValue;
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
	 * 设置：目标文件的列名
	 */
	public void setTargetColumn(String targetColumn) {
		this.targetColumn = targetColumn;
	}
	/**
	 * 获取：目标文件的列名
	 */
	public String getTargetColumn() {
		return targetColumn;
	}
	/**
	 * 设置：
	 */
	public void setTableA(String tableA) {
		this.tableA = tableA;
	}
	/**
	 * 获取：
	 */
	public String getTableA() {
		return tableA;
	}
	/**
	 * 设置：第一个值
	 */
	public void setColumnA(String columnA) {
		this.columnA = columnA;
	}
	/**
	 * 获取：第一个值
	 */
	public String getColumnA() {
		return columnA;
	}
	/**
	 * 设置：(加1，减2，乘3，除4，替换5)
	 */
	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}
	/**
	 * 获取：(加1，减2，乘3，除4，替换5)
	 */
	public String getCalculation() {
		return calculation;
	}
	/**
	 * 设置：
	 */
	public void setTableB(String tableB) {
		this.tableB = tableB;
	}
	/**
	 * 获取：
	 */
	public String getTableB() {
		return tableB;
	}
	/**
	 * 设置：第二个值
	 */
	public void setColumnB(String columnB) {
		this.columnB = columnB;
	}
	/**
	 * 获取：第二个值
	 */
	public String getColumnB() {
		return columnB;
	}
	/**
	 * 设置：(加1，减2，乘3，除4，替换5)
	 */
	public void setCalculations(String calculations) {
		this.calculations = calculations;
	}
	/**
	 * 获取：(加1，减2，乘3，除4，替换5)
	 */
	public String getCalculations() {
		return calculations;
	}
	/**
	 * 设置：
	 */
	public void setTableC(String tableC) {
		this.tableC = tableC;
	}
	/**
	 * 获取：
	 */
	public String getTableC() {
		return tableC;
	}
	/**
	 * 设置：第三个值
	 */
	public void setColumnC(String columnC) {
		this.columnC = columnC;
	}
	/**
	 * 获取：第三个值
	 */
	public String getColumnC() {
		return columnC;
	}
	/**
	 * 设置：随机数
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * 获取：随机数
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * 设置：第一个表的基准列
	 */
	public void setBenchmarkA(String benchmarkA) {
		this.benchmarkA = benchmarkA;
	}
	/**
	 * 获取：第一个表的基准列
	 */
	public String getBenchmarkA() {
		return benchmarkA;
	}
	/**
	 * 设置：第二个表的基准列
	 */
	public void setBenchmarkB(String benchmarkB) {
		this.benchmarkB = benchmarkB;
	}
	/**
	 * 获取：第二个表的基准列
	 */
	public String getBenchmarkB() {
		return benchmarkB;
	}
	/**
	 * 设置：第三个表的基准列
	 */
	public void setBenchmarkC(String benchmarkC) {
		this.benchmarkC = benchmarkC;
	}
	/**
	 * 获取：第三个表的基准列
	 */
	public String getBenchmarkC() {
		return benchmarkC;
	}

	/**
	 * 获取：默认值
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * 设置：默认值
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
