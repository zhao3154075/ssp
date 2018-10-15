package com.es.ssp.query;

import cn.org.rapid_framework.util.DateConvertUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import cn.org.rapid_framework.page.PageRequest;

import static common.util.DateFormats.DATE_TIME_MIN_FORMAT;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
public class PrizeRecordQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** recordId */
	private Long recordId;
	/** 粉丝id */
	private Integer fansId;
	/** 创建时间 */
	private Long createTime;
	/** 状态 */
	private Integer status;
	/** 举报信息id（外键） */
	private Long reportId;
	/** 类型（初次还是追加） */
	private Integer type;
	/** 金额 */
	private Integer amount;
	/** 错误信息 */
	private String errorInfo;
	/** 红包订单号 */
	private String billno;
	/** openId */
	private String openId;
	/** 昵称 */
	private String nickName;

	private Long startTime;

	private Long endTime;

	private boolean byTime;

	private Long startReportTime;

	private Long endReportTime;

	private boolean byReportTime;

	public Long getStartTime() {
		return startTime==null?System.currentTimeMillis()/1000:startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTime = DateConvertUtils.parse(startTimeStr, DATE_TIME_MIN_FORMAT,java.util.Date.class).getTime()/1000;
	}

	public Long getEndTime() {
		return endTime==null?System.currentTimeMillis()/1000:endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTime = DateConvertUtils.parse(endTimeStr, DATE_TIME_MIN_FORMAT,java.util.Date.class).getTime()/1000;
	}
	public boolean isByTime() {
		return byTime;
	}

	public void setByTime(boolean byTime) {
		this.byTime = byTime;
	}

	public Long getStartReportTime() {
		return startReportTime==null?System.currentTimeMillis()/1000:startReportTime;
	}

	public void setStartReportTimeStr(String startReportTimeStr) {
		this.startReportTime = DateConvertUtils.parse(startReportTimeStr, DATE_TIME_MIN_FORMAT,java.util.Date.class).getTime()/1000;
	}
	public void setStartReportTime(Long startReportTime) {
		this.startReportTime = startReportTime;
	}

	public Long getEndReportTime() {
		return endReportTime==null?System.currentTimeMillis()/1000:endReportTime;
	}

	public void setEndReportTime(Long endReportTime) {
		this.endReportTime = endReportTime;
	}
	public void setEndReportTimeStr(String endReportTimeStr) {
		this.endReportTime = DateConvertUtils.parse(endReportTimeStr, DATE_TIME_MIN_FORMAT,java.util.Date.class).getTime()/1000;
	}
	public boolean isByReportTime() {
		return byReportTime;
	}

	public void setByReportTime(boolean byReportTime) {
		this.byReportTime = byReportTime;
	}

	public Long getRecordId() {
		return this.recordId;
	}
	
	public void setRecordId(Long value) {
		this.recordId = value;
	}
	
	public Integer getFansId() {
		return this.fansId;
	}
	
	public void setFansId(Integer value) {
		this.fansId = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Long value) {
		this.createTime = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Long getReportId() {
		return this.reportId;
	}
	
	public void setReportId(Long value) {
		this.reportId = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer value) {
		this.amount = value;
	}
	
	public String getErrorInfo() {
		return this.errorInfo;
	}
	
	public void setErrorInfo(String value) {
		this.errorInfo = value;
	}
	
	public String getBillno() {
		return this.billno;
	}
	
	public void setBillno(String value) {
		this.billno = value;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

