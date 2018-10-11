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
public class RolePermissionQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** rpId */
	private Integer rpId;
	/** 角色ID */
	private Integer roleId;
	/** 权限ID */
	private Integer permissionId;

	public Integer getRpId() {
		return this.rpId;
	}
	
	public void setRpId(Integer value) {
		this.rpId = value;
	}
	
	public Integer getRoleId() {
		return this.roleId;
	}
	
	public void setRoleId(Integer value) {
		this.roleId = value;
	}
	
	public Integer getPermissionId() {
		return this.permissionId;
	}
	
	public void setPermissionId(Integer value) {
		this.permissionId = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

