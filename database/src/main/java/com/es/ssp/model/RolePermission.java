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
public class RolePermission  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * rpId       db_column: rpId 
     */	
	private Integer rpId;
    /**
     * 角色ID       db_column: roleId 
     */	
	private Integer roleId;
    /**
     * 权限ID       db_column: permissionId 
     */	
	private Integer permissionId;
	//columns END

	public RolePermission(){
	}

	public RolePermission(
		Integer rpId
	){
		this.rpId = rpId;
	}

	public void setRpId(Integer value) {
		this.rpId = value;
	}
	
	public Integer getRpId() {
		return this.rpId;
	}
	public void setRoleId(Integer value) {
		this.roleId = value;
	}
	
	public Integer getRoleId() {
		return this.roleId;
	}
	public void setPermissionId(Integer value) {
		this.permissionId = value;
	}
	
	public Integer getPermissionId() {
		return this.permissionId;
	}
	
	private Permission permission;
	
	public void setPermission(Permission permission){
		this.permission = permission;
	}
	
	public Permission getPermission() {
		return permission;
	}
	
	private Role role;
	
	public void setRole(Role role){
		this.role = role;
	}
	
	public Role getRole() {
		return role;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRpId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RolePermission == false) return false;
		if(this == obj) return true;
		RolePermission other = (RolePermission)obj;
		return new EqualsBuilder()
			.append(getRpId(),other.getRpId())
			.isEquals();
	}
}

