package com.es.ssp.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.*;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
public class OperateRecord  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * recordId       db_column: recordId 
     */	
	private Long recordId;
    /**
     * reportId       db_column: reportId 
     */	
	private Long reportId;
    /**
     * userId       db_column: userId 
     */	
	private Integer userId;
    /**
     * 创建时间       db_column: createTime 
     */	
	private Long createTime;
    /**
     * 描述       db_column: desc 
     */	
	private String desc;
	//columns END

	public OperateRecord(){
	}

	public OperateRecord(
		Long recordId
	){
		this.recordId = recordId;
	}

	public void setRecordId(Long value) {
		this.recordId = value;
	}
	
	public Long getRecordId() {
		return this.recordId;
	}
	public void setReportId(Long value) {
		this.reportId = value;
	}
	
	public Long getReportId() {
		return this.reportId;
	}
	public void setUserId(Integer value) {
		this.userId = value;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	public void setCreateTime(Long value) {
		this.createTime = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}
	public void setDesc(String value) {
		this.desc = value;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	private Report report;
	
	public void setReport(Report report){
		this.report = report;
	}
	
	public Report getReport() {
		return report;
	}
	
	private LoginUser loginUser;
	
	public void setLoginUser(LoginUser loginUser){
		this.loginUser = loginUser;
	}
	
	public LoginUser getLoginUser() {
		return loginUser;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRecordId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof OperateRecord == false) return false;
		if(this == obj) return true;
		OperateRecord other = (OperateRecord)obj;
		return new EqualsBuilder()
			.append(getRecordId(),other.getRecordId())
			.isEquals();
	}
}

