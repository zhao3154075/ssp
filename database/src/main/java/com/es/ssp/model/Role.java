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
public class Role  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * roleId       db_column: roleId 
     */	
	private Integer roleId;
    /**
     * 角色名称       db_column: name 
     */	
	private String name;
    /**
     * 角色标识       db_column: identification 
     */	
	private String identification;
	//columns END

	public Role(){
	}

	public Role(
		Integer roleId
	){
		this.roleId = roleId;
	}

	public void setRoleId(Integer value) {
		this.roleId = value;
	}
	
	public Integer getRoleId() {
		return this.roleId;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setIdentification(String value) {
		this.identification = value;
	}
	
	public String getIdentification() {
		return this.identification;
	}
	
	private Set rolePermissions = new HashSet(0);
	public void setRolePermissions(Set<RolePermission> rolePermission){
		this.rolePermissions = rolePermission;
	}
	
	public Set<RolePermission> getRolePermissions() {
		return rolePermissions;
	}
	
	private Set userRoles = new HashSet(0);
	public void setUserRoles(Set<UserRole> userRole){
		this.userRoles = userRole;
	}
	
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRoleId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Role == false) return false;
		if(this == obj) return true;
		Role other = (Role)obj;
		return new EqualsBuilder()
			.append(getRoleId(),other.getRoleId())
			.isEquals();
	}
}

