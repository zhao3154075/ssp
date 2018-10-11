package com.es.ssp.model;

import org.apache.commons.lang.builder.*;

import java.io.*;
import java.util.*;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
public class Report  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * 举报id       db_column: reportId 
     */	
	private Long reportId;
    /**
     * 粉丝id（外键）       db_column: fansId 
     */	
	private Integer fansId;
    /**
     * 创建时间       db_column: createTime 
     */	
	private Long createTime;
    /**
     * 发生时间       db_column: happenTime 
     */	
	private Long happenTime;
    /**
     * 发生地点       db_column: happenPlace 
     */	
	private String happenPlace;
    /**
     * 事件描述       db_column: eventDesc 
     */	
	private String eventDesc;
    /**
     * 描述语音       db_column: descVoice 
     */	
	private String descVoice;
    /**
     * 佐证材料图片       db_column: descImages 
     */	
	private String descImages;
    /**
     * 佐证材料视频       db_column: descVideo 
     */	
	private String descVideo;
    /**
     * 状态       db_column: status 
     */	
	private Integer status;
    /**
     * 分类1       db_column: reportType1 
     */	
	private Integer reportType1;
    /**
     * 分类2       db_column: reportType2 
     */	
	private Integer reportType2;
    /**
     * 初次奖励状态       db_column: prizeStatus1 
     */	
	private Integer prizeStatus1;
    /**
     * 追加奖励状态       db_column: prizeStatus2 
     */	
	private Integer prizeStatus2;
    /**
     * 备注       db_column: remark 
     */	
	private String remark;
    /**
     * 小编回复       db_column: reply 
     */	
	private String reply;
	private Integer isHide;
	//columns END

	public Report(){
	}

	public Report(
		Long reportId
	){
		this.reportId = reportId;
	}

	public void setReportId(Long value) {
		this.reportId = value;
	}
	
	public Long getReportId() {
		return this.reportId;
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
	public void setHappenTime(Long value) {
		this.happenTime = value;
	}
	
	public Long getHappenTime() {
		return this.happenTime;
	}
	public void setHappenPlace(String value) {
		this.happenPlace = value;
	}
	
	public String getHappenPlace() {
		return this.happenPlace;
	}
	public void setEventDesc(String value) {
		this.eventDesc = value;
	}
	
	public String getEventDesc() {
		return this.eventDesc;
	}
	public void setDescVoice(String value) {
		this.descVoice = value;
	}
	
	public String getDescVoice() {
		return this.descVoice;
	}
	public void setDescImages(String value) {
		this.descImages = value;
	}
	
	public String getDescImages() {
		return this.descImages;
	}
	public void setDescVideo(String value) {
		this.descVideo = value;
	}
	
	public String getDescVideo() {
		return this.descVideo;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setReportType1(Integer value) {
		this.reportType1 = value;
	}
	
	public Integer getReportType1() {
		return this.reportType1;
	}
	public void setReportType2(Integer value) {
		this.reportType2 = value;
	}
	
	public Integer getReportType2() {
		return this.reportType2;
	}
	public void setPrizeStatus1(Integer value) {
		this.prizeStatus1 = value;
	}
	
	public Integer getPrizeStatus1() {
		return this.prizeStatus1;
	}
	public void setPrizeStatus2(Integer value) {
		this.prizeStatus2 = value;
	}
	
	public Integer getPrizeStatus2() {
		return this.prizeStatus2;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setReply(String value) {
		this.reply = value;
	}
	
	public String getReply() {
		return this.reply;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public String getStatusString(){
		switch (status){
			case 0:return "待初审";
			case 1:return "待复审";
			case 2:return "初审未通过";
			case 3:return "待核实";
			case 4:return "复审未通过";
			case 5:return "待整改";
			case 6:return "信息不准确";
			case 7:return "已整改";
		}
		return "";
	}

	private Set operateRecords = new HashSet(0);
	public void setOperateRecords(Set<OperateRecord> operateRecord){
		this.operateRecords = operateRecord;
	}
	
	public Set<OperateRecord> getOperateRecords() {
		return operateRecords;
	}
	
	private Set prizeRecords = new HashSet(0);
	public void setPrizeRecords(Set<PrizeRecord> prizeRecord){
		this.prizeRecords = prizeRecord;
	}
	
	public Set<PrizeRecord> getPrizeRecords() {
		return prizeRecords;
	}
	
	private ReportType reportType;
	
	public void setReportType(ReportType reportType){
		this.reportType = reportType;
	}
	
	public ReportType getReportType() {
		return reportType;
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
			.append(getReportId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Report == false) return false;
		if(this == obj) return true;
		Report other = (Report)obj;
		return new EqualsBuilder()
			.append(getReportId(),other.getReportId())
			.isEquals();
	}
}

