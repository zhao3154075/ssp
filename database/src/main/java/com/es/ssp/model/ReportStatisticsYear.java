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
public class ReportStatisticsYear  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * recordId       db_column: recordId 
     */	
	private Integer recordId;
    /**
     * fansId       db_column: fansId 
     */	
	private Integer fansId;
    /**
     * totalAmount       db_column: totalAmount 
     */	
	private Integer totalAmount;
    /**
     * year       db_column: year 
     */	
	private Integer year;
	//columns END

	public ReportStatisticsYear(){
	}

	public ReportStatisticsYear(
		Integer recordId
	){
		this.recordId = recordId;
	}

	public void setRecordId(Integer value) {
		this.recordId = value;
	}
	
	public Integer getRecordId() {
		return this.recordId;
	}
	public void setFansId(Integer value) {
		this.fansId = value;
	}
	
	public Integer getFansId() {
		return this.fansId;
	}
	public void setTotalAmount(Integer value) {
		this.totalAmount = value;
	}
	
	public Integer getTotalAmount() {
		return this.totalAmount;
	}
	public void setYear(Integer value) {
		this.year = value;
	}
	
	public Integer getYear() {
		return this.year;
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
		if(obj instanceof ReportStatisticsYear == false) return false;
		if(this == obj) return true;
		ReportStatisticsYear other = (ReportStatisticsYear)obj;
		return new EqualsBuilder()
			.append(getRecordId(),other.getRecordId())
			.isEquals();
	}
}

