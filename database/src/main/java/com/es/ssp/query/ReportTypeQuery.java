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
public class ReportTypeQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 分类id */
	private Integer typeId;
	/** 分类名称 */
	private String typeName;
	/** 分类描述 */
	private String typeDesc;
	/** 奖励金额 */
	private Integer amount;
	/** 创建时间 */
	private Long createTime;
	private Integer parentId;

	private String children;

	public Integer getTypeId() {
		return this.typeId;
	}
	
	public void setTypeId(Integer value) {
		this.typeId = value;
	}
	
	public String getTypeName() {
		return this.typeName;
	}
	
	public void setTypeName(String value) {
		this.typeName = value;
	}
	
	public String getTypeDesc() {
		return this.typeDesc;
	}
	
	public void setTypeDesc(String value) {
		this.typeDesc = value;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer value) {
		this.amount = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Long value) {
		this.createTime = value;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

