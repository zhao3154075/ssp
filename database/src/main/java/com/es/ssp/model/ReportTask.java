package com.es.ssp.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.*;
import java.util.*;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
public class ReportTask  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ReportTask";
	public static final String ALIAS_TASK_ID = "taskId";
	public static final String ALIAS_REAL_NAME = "realName";
	public static final String ALIAS_MOBILE = "mobile";
	public static final String ALIAS_WORK_UNIT = "workUnit";
	public static final String ALIAS_TOWN = "town";
	public static final String ALIAS_COMMUNITY = "community";
	public static final String ALIAS_TASK_NUM = "taskNum";
	public static final String ALIAS_CREATE_TIME = "createTime";
	
	//date formats
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * taskId       db_column: taskId 
     */	
	private Integer taskId;
    /**
     * realName       db_column: realName 
     */	
	private String realName;
    /**
     * mobile       db_column: mobile 
     */	
	private String mobile;
    /**
     * workUnit       db_column: workUnit 
     */	
	private String workUnit;
    /**
     * town       db_column: town 
     */	
	private String town;
    /**
     * community       db_column: community 
     */	
	private String community;
    /**
     * taskNum       db_column: taskNum 
     */	
	private Integer taskNum;
    /**
     * createTime       db_column: createTime 
     */	
	private Long createTime;
	//columns END

	public ReportTask(){
	}

	public ReportTask(
		Integer taskId
	){
		this.taskId = taskId;
	}

	public void setTaskId(Integer value) {
		this.taskId = value;
	}
	
	public Integer getTaskId() {
		return this.taskId;
	}
	public void setRealName(String value) {
		this.realName = value;
	}
	
	public String getRealName() {
		return this.realName;
	}
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	public void setWorkUnit(String value) {
		this.workUnit = value;
	}
	
	public String getWorkUnit() {
		return this.workUnit;
	}
	public void setTown(String value) {
		this.town = value;
	}
	
	public String getTown() {
		return this.town;
	}
	public void setCommunity(String value) {
		this.community = value;
	}
	
	public String getCommunity() {
		return this.community;
	}
	public void setTaskNum(Integer value) {
		this.taskNum = value;
	}
	
	public Integer getTaskNum() {
		return this.taskNum;
	}
	public void setCreateTime(Long value) {
		this.createTime = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}
	
	private Set taskStatisticss = new HashSet(0);
	public void setTaskStatisticss(Set<TaskStatistics> taskStatistics){
		this.taskStatisticss = taskStatistics;
	}
	
	public Set<TaskStatistics> getTaskStatisticss() {
		return taskStatisticss;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getTaskId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ReportTask == false) return false;
		if(this == obj) return true;
		ReportTask other = (ReportTask)obj;
		return new EqualsBuilder()
			.append(getTaskId(),other.getTaskId())
			.isEquals();
	}
}

