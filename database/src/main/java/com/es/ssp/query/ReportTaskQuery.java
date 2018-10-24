package com.es.ssp.query;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


import cn.org.rapid_framework.page.PageRequest;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
public class ReportTaskQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** taskId */
	private Integer taskId;
	/** realName */
	private String realName;
	/** mobile */
	private String mobile;
	/** workUnit */
	private String workUnit;
	/** town */
	private String town;
	/** community */
	private String community;
	/** taskNum */
	private Integer taskNum;
	/** createTime */
	private Long createTime;

	public Integer getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(Integer value) {
		this.taskId = value;
	}
	
	public String getRealName() {
		return this.realName;
	}
	
	public void setRealName(String value) {
		this.realName = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getWorkUnit() {
		return this.workUnit;
	}
	
	public void setWorkUnit(String value) {
		this.workUnit = value;
	}
	
	public String getTown() {
		return this.town;
	}
	
	public void setTown(String value) {
		this.town = value;
	}
	
	public String getCommunity() {
		return this.community;
	}
	
	public void setCommunity(String value) {
		this.community = value;
	}
	
	public Integer getTaskNum() {
		return this.taskNum;
	}
	
	public void setTaskNum(Integer value) {
		this.taskNum = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Long value) {
		this.createTime = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

