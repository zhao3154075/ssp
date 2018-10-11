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
public class Fans  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * 粉丝ID       db_column: fansId 
     */	
	private Integer fansId;
    /**
     * openId       db_column: openId 
     */	
	private String openId;
    /**
     * 昵称       db_column: nickName 
     */	
	private String nickName;
    /**
     * 姓名       db_column: realName 
     */	
	private String realName;
    /**
     * 联系方式       db_column: mobile 
     */	
	private String mobile;
    /**
     * 创建时间       db_column: createTime 
     */	
	private Long createTime;
	/**
	 * 头像      db_column: headimgurl
	 */
	private String headimgurl;
	/**
	 * 是否关注      db_column: subscribe
	 */
	private Integer subscribe;
	//columns END

	public Fans(){
	}

	public Fans(
		Integer fansId
	){
		this.fansId = fansId;
	}

	public void setFansId(Integer value) {
		this.fansId = value;
	}
	
	public Integer getFansId() {
		return this.fansId;
	}
	public void setOpenId(String value) {
		this.openId = value;
	}
	
	public String getOpenId() {
		return this.openId;
	}
	public void setNickName(String value) {
		this.nickName = value;
	}
	
	public String getNickName() {
		return this.nickName;
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
	public void setCreateTime(Long value) {
		this.createTime = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	private Set reports = new HashSet(0);
	public void setReports(Set<Report> report){
		this.reports = report;
	}
	
	public Set<Report> getReports() {
		return reports;
	}
	
	private Set prizeRecords = new HashSet(0);
	public void setPrizeRecords(Set<PrizeRecord> prizeRecord){
		this.prizeRecords = prizeRecord;
	}
	
	public Set<PrizeRecord> getPrizeRecords() {
		return prizeRecords;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getFansId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Fans == false) return false;
		if(this == obj) return true;
		Fans other = (Fans)obj;
		return new EqualsBuilder()
			.append(getFansId(),other.getFansId())
			.isEquals();
	}
}

