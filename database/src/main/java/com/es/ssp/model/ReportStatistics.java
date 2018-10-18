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
public class ReportStatistics  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * recordId       db_column: recordId 
     */	
	private Integer recordId;
    /**
     * 信息数量       db_column: totalReport 
     */	
	private Integer totalReport;
    /**
     * 待核实数量       db_column: totalStatus1 
     */	
	private Integer totalStatus1;
    /**
     * 已核实待处理数量       db_column: totalStatus2 
     */	
	private Integer totalStatus2;
    /**
     * 已处理数量       db_column: totalStatus3 
     */	
	private Integer totalStatus3;
    /**
     * 信息不准确数量       db_column: totalStatus4 
     */	
	private Integer totalStatus4;
	private Integer totalStatus5;
	private Integer totalStatus6;
	private Integer totalStatus7;
	private Integer totalStatus8;
    /**
     * 总贡献值       db_column: totalAmount 
     */	
	private Integer totalAmount;
    /**
     * fansId       db_column: fansId 
     */	
	private Integer fansId;
	private Integer year;
	private Long updateTime;
	//columns END

	public ReportStatistics(){
	}

	public ReportStatistics(
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
	public void setTotalReport(Integer value) {
		this.totalReport = value;
	}
	
	public Integer getTotalReport() {
		return this.totalReport;
	}
	public void setTotalStatus1(Integer value) {
		this.totalStatus1 = value;
	}
	
	public Integer getTotalStatus1() {
		return this.totalStatus1;
	}
	public void setTotalStatus2(Integer value) {
		this.totalStatus2 = value;
	}
	
	public Integer getTotalStatus2() {
		return this.totalStatus2;
	}
	public void setTotalStatus3(Integer value) {
		this.totalStatus3 = value;
	}
	
	public Integer getTotalStatus3() {
		return this.totalStatus3;
	}
	public void setTotalStatus4(Integer value) {
		this.totalStatus4 = value;
	}
	
	public Integer getTotalStatus4() {
		return this.totalStatus4;
	}
	public void setTotalAmount(Integer value) {
		this.totalAmount = value;
	}

	public Integer getTotalStatus5() {
		return totalStatus5;
	}

	public void setTotalStatus5(Integer totalStatus5) {
		this.totalStatus5 = totalStatus5;
	}

	public Integer getTotalStatus6() {
		return totalStatus6;
	}

	public void setTotalStatus6(Integer totalStatus6) {
		this.totalStatus6 = totalStatus6;
	}

	public Integer getTotalStatus7() {
		return totalStatus7;
	}

	public void setTotalStatus7(Integer totalStatus7) {
		this.totalStatus7 = totalStatus7;
	}

	public Integer getTotalStatus8() {
		return totalStatus8;
	}

	public void setTotalStatus8(Integer totalStatus8) {
		this.totalStatus8 = totalStatus8;
	}

	public Integer getTotalAmount() {
		return this.totalAmount;
	}
	public void setFansId(Integer value) {
		this.fansId = value;
	}
	
	public Integer getFansId() {
		return this.fansId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	private Fans fans;
	
	public void setFans(Fans fans){
		this.fans = fans;
	}
	
	public Fans getFans() {
		return fans;
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
		if(obj instanceof ReportStatistics == false) return false;
		if(this == obj) return true;
		ReportStatistics other = (ReportStatistics)obj;
		return new EqualsBuilder()
			.append(getRecordId(),other.getRecordId())
			.isEquals();
	}
}

