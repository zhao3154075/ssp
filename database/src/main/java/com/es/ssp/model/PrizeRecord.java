package com.es.ssp.model;

import org.apache.commons.lang.builder.*;
import java.io.*;
/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
public class PrizeRecord  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * recordId       db_column: recordId 
     */	
	private Long recordId;
    /**
     * 粉丝id       db_column: fansId 
     */	
	private Integer fansId;
    /**
     * 创建时间       db_column: createTime 
     */	
	private Long createTime;
    /**
     * 状态       db_column: status 
     */	
	private Integer status;
    /**
     * 举报信息id（外键）       db_column: reportId 
     */	
	private Long reportId;
    /**
     * 类型（初次还是追加）       db_column: type 
     */	
	private Integer type;
    /**
     * 金额       db_column: amount 
     */	
	private Integer amount;
    /**
     * 错误信息       db_column: errorInfo 
     */	
	private String errorInfo;
    /**
     * 红包订单号       db_column: billno 
     */	
	private String billno;
	private Long reportTime;
	//columns END

	public PrizeRecord(){
	}

	public PrizeRecord(
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
	public void setFansId(Integer value) {
		this.fansId = value;
	}
	
	public Integer getFansId() {
		return this.fansId;
	}
	public void setCreateTime(Long value) {
		this.createTime = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public String getStatusStr(){
		switch (this.status){
			case -1:return "发送失败";
			case 0:return "待发送";
			case 1:return "发送成功";
			case 2:return "发送中";
		}
		return "";
	}
	public void setReportId(Long value) {
		this.reportId = value;
	}
	
	public Long getReportId() {
		return this.reportId;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setAmount(Integer value) {
		this.amount = value;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	public void setErrorInfo(String value) {
		this.errorInfo = value;
	}
	
	public String getErrorInfo() {
		return this.errorInfo;
	}
	public void setBillno(String value) {
		this.billno = value;
	}
	
	public String getBillno() {
		return this.billno;
	}

	public Long getReportTime() {
		return reportTime;
	}

	public void setReportTime(Long reportTime) {
		this.reportTime = reportTime;
	}

	private Report report;
	
	public void setReport(Report report){
		this.report = report;
	}
	
	public Report getReport() {
		return report;
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
		if(obj instanceof PrizeRecord == false) return false;
		if(this == obj) return true;
		PrizeRecord other = (PrizeRecord)obj;
		return new EqualsBuilder()
			.append(getRecordId(),other.getRecordId())
			.isEquals();
	}
}

