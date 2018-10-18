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
public class ReportStatisticsYearQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** recordId */
	private Integer recordId;
	/** fansId */
	private Integer fansId;
	/** totalAmount */
	private Integer totalAmount;

	public Integer getRecordId() {
		return this.recordId;
	}
	
	public void setRecordId(Integer value) {
		this.recordId = value;
	}
	
	public Integer getFansId() {
		return this.fansId;
	}
	
	public void setFansId(Integer value) {
		this.fansId = value;
	}
	
	public Integer getTotalAmount() {
		return this.totalAmount;
	}
	
	public void setTotalAmount(Integer value) {
		this.totalAmount = value;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

