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
public class GlobalSetting  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * settingId       db_column: settingId 
     */	
	private Integer settingId;
    /**
     * 每日奖励发放上限       db_column: dayLimit 
     */	
	private Integer dayLimit;
    /**
     * 初始红包金额       db_column: firstAmount 
     */	
	private Integer firstAmount;
    /**
     * 领取方式       db_column: receiveType 
     */	
	private Integer receiveType;
    /**
     * 最后修改时间       db_column: updateTime 
     */	
	private Long updateTime;
	//columns END

	public GlobalSetting(){
	}

	public GlobalSetting(
		Integer settingId
	){
		this.settingId = settingId;
	}

	public void setSettingId(Integer value) {
		this.settingId = value;
	}
	
	public Integer getSettingId() {
		return this.settingId;
	}
	public void setDayLimit(Integer value) {
		this.dayLimit = value;
	}
	
	public Integer getDayLimit() {
		return this.dayLimit;
	}
	public void setFirstAmount(Integer value) {
		this.firstAmount = value;
	}
	
	public Integer getFirstAmount() {
		return this.firstAmount;
	}
	public void setReceiveType(Integer value) {
		this.receiveType = value;
	}
	
	public Integer getReceiveType() {
		return this.receiveType;
	}
	public void setUpdateTime(Long value) {
		this.updateTime = value;
	}
	
	public Long getUpdateTime() {
		return this.updateTime;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getSettingId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof GlobalSetting == false) return false;
		if(this == obj) return true;
		GlobalSetting other = (GlobalSetting)obj;
		return new EqualsBuilder()
			.append(getSettingId(),other.getSettingId())
			.isEquals();
	}
}

