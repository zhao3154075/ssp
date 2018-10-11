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
public class Permission  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * permissionId       db_column: permissionId 
     */	
	private Integer permissionId;
    /**
     * 权限标识       db_column: identification 
     */	
	private String identification;
    /**
     * url描述       db_column: name 
     */	
	private String name;
	//columns END

	public Permission(){
	}

	public Permission(
		Integer permissionId
	){
		this.permissionId = permissionId;
	}

	public void setPermissionId(Integer value) {
		this.permissionId = value;
	}
	
	public Integer getPermissionId() {
		return this.permissionId;
	}
	public void setIdentification(String value) {
		this.identification = value;
	}
	
	public String getIdentification() {
		return this.identification;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	private Set rolePermissions = new HashSet(0);
	public void setRolePermissions(Set<RolePermission> rolePermission){
		this.rolePermissions = rolePermission;
	}
	
	public Set<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPermissionId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Permission == false) return false;
		if(this == obj) return true;
		Permission other = (Permission)obj;
		return new EqualsBuilder()
			.append(getPermissionId(),other.getPermissionId())
			.isEquals();
	}
}

