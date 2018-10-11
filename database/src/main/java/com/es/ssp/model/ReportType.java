package com.es.ssp.model;

import org.apache.commons.lang.builder.*;
import java.io.*;
import java.util.*;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
public class ReportType  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * 分类id       db_column: typeId 
     */	
	private Integer typeId;
    /**
     * 分类名称       db_column: typeName 
     */	
	private String typeName;
    /**
     * 分类描述       db_column: typeDesc 
     */	
	private String typeDesc;
    /**
     * 奖励金额       db_column: amount 
     */	
	private Integer amount;
    /**
     * 创建时间       db_column: createTime 
     */	
	private Long createTime;
	/**
	 * 父节点id       db_column: parentId
	 */
	private Integer parentId;
	//columns END

	public ReportType(){
	}

	public ReportType(
		Integer typeId
	){
		this.typeId = typeId;
	}

	public void setTypeId(Integer value) {
		this.typeId = value;
	}
	
	public Integer getTypeId() {
		return this.typeId;
	}
	public void setTypeName(String value) {
		this.typeName = value;
	}
	
	public String getTypeName() {
		return this.typeName;
	}
	public void setTypeDesc(String value) {
		this.typeDesc = value;
	}
	
	public String getTypeDesc() {
		return this.typeDesc;
	}
	public void setAmount(Integer value) {
		this.amount = value;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	public void setCreateTime(Long value) {
		this.createTime = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	private Set reports = new HashSet(0);
	public void setReports(Set<Report> report){
		this.reports = report;
	}
	
	public Set<Report> getReports() {
		return reports;
	}


	private ReportType parentType;

	public ReportType getParentType() {
		return parentType;
	}

	public void setParentType(ReportType parentType) {
		this.parentType = parentType;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getTypeId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ReportType == false) return false;
		if(this == obj) return true;
		ReportType other = (ReportType)obj;
		return new EqualsBuilder()
			.append(getTypeId(),other.getTypeId())
			.isEquals();
	}
}

