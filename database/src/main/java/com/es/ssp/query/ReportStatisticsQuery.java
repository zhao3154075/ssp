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
public class ReportStatisticsQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** recordId */
	private Integer recordId;
	/** 信息数量 */
	private Integer totalReport;
	/** 待核实数量 */
	private Integer totalStatus1;
	/** 已核实待处理数量 */
	private Integer totalStatus2;
	/** 已处理数量 */
	private Integer totalStatus3;
	/** 信息不准确数量 */
	private Integer totalStatus4;
	/** 总贡献值 */
	private Integer totalAmount;
	/** fansId */
	private Integer fansId;
	private String openId;
	private String nickName;
	private String realName;
	private String mobile;
	private Integer year;
	private Long updateTime;

	public Integer getRecordId() {
		return this.recordId;
	}
	
	public void setRecordId(Integer value) {
		this.recordId = value;
	}
	
	public Integer getTotalReport() {
		return this.totalReport;
	}
	
	public void setTotalReport(Integer value) {
		this.totalReport = value;
	}
	
	public Integer getTotalStatus1() {
		return this.totalStatus1;
	}
	
	public void setTotalStatus1(Integer value) {
		this.totalStatus1 = value;
	}
	
	public Integer getTotalStatus2() {
		return this.totalStatus2;
	}
	
	public void setTotalStatus2(Integer value) {
		this.totalStatus2 = value;
	}
	
	public Integer getTotalStatus3() {
		return this.totalStatus3;
	}
	
	public void setTotalStatus3(Integer value) {
		this.totalStatus3 = value;
	}
	
	public Integer getTotalStatus4() {
		return this.totalStatus4;
	}
	
	public void setTotalStatus4(Integer value) {
		this.totalStatus4 = value;
	}
	
	public Integer getTotalAmount() {
		return this.totalAmount;
	}
	
	public void setTotalAmount(Integer value) {
		this.totalAmount = value;
	}
	
	public Integer getFansId() {
		return this.fansId;
	}
	
	public void setFansId(Integer value) {
		this.fansId = value;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

