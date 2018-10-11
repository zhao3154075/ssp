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
public class PermissionQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** permissionId */
	private Integer permissionId;
	/** 权限标识 */
	private String identification;
	/** url描述 */
	private String name;

	public Integer getPermissionId() {
		return this.permissionId;
	}
	
	public void setPermissionId(Integer value) {
		this.permissionId = value;
	}
	
	public String getIdentification() {
		return this.identification;
	}
	
	public void setIdentification(String value) {
		this.identification = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

