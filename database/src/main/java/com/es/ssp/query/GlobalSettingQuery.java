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
public class GlobalSettingQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** settingId */
	private Integer settingId;
	/** 每日奖励发放上限 */
	private Integer dayLimit;
	/** 初始红包金额 */
	private Integer firstAmount;
	/** 领取方式 */
	private Integer receiveType;
	/** 最后修改时间 */
	private Long updateTime;

	public Integer getSettingId() {
		return this.settingId;
	}
	
	public void setSettingId(Integer value) {
		this.settingId = value;
	}
	
	public Integer getDayLimit() {
		return this.dayLimit;
	}
	
	public void setDayLimit(Integer value) {
		this.dayLimit = value;
	}
	
	public Integer getFirstAmount() {
		return this.firstAmount;
	}
	
	public void setFirstAmount(Integer value) {
		this.firstAmount = value;
	}
	
	public Integer getReceiveType() {
		return this.receiveType;
	}
	
	public void setReceiveType(Integer value) {
		this.receiveType = value;
	}
	
	public Long getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(Long value) {
		this.updateTime = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

