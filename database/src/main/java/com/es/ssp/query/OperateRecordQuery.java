package com.es.ssp.query;

import cn.org.rapid_framework.page.PageRequest;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
public class OperateRecordQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** recordId */
	private Long recordId;
	/** reportId */
	private Long reportId;
	/** userId */
	private Integer userId;
	/** 创建时间 */
	private Long createTime;
	/** 描述 */
	private String desc;

	public Long getRecordId() {
		return this.recordId;
	}
	
	public void setRecordId(Long value) {
		this.recordId = value;
	}
	
	public Long getReportId() {
		return this.reportId;
	}
	
	public void setReportId(Long value) {
		this.reportId = value;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public void setUserId(Integer value) {
		this.userId = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Long value) {
		this.createTime = value;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public void setDesc(String value) {
		this.desc = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

