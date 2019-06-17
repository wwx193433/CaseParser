package com.huawei;

import java.io.Serializable;

/**
 * @title 执行用例
 * @desc  xxxx
 * @author wwx193433  
 * @date 2019年6月15日
 */
public class CaseBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public CaseBean() {
	}
	//用例id
	private String caseId;
	//用例名称
	private String name;
	//用例number
	private String number;
	//阶段
	private String stage;
	//执行周期
	private String round;
	//域
	private String tep;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	//耗时（秒）
	private String timeCost;
	//测试特性
	private String testFea;
	//执行结果
	private String result;
	
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	public String getTep() {
		return tep;
	}
	public void setTep(String tep) {
		this.tep = tep;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTimeCost() {
		return timeCost;
	}
	public void setTimeCost(String timeCost) {
		this.timeCost = timeCost;
	}
	public String getTestFea() {
		return testFea;
	}
	public void setTestFea(String testFea) {
		this.testFea = testFea;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getIp() {
		if(null!=this.tep && this.tep.contains(":")) {
			return this.tep.split(":")[0];
		}
		return "";
	}
	
}
