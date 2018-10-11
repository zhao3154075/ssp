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
public class UserRole  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * urId       db_column: urId 
     */	
	private Integer urId;
    /**
     * 用户ID       db_column: userId 
     */	
	private Integer userId;
    /**
     * 角色ID       db_column: roleId 
     */	
	private Integer roleId;
	//columns END

	public UserRole(){
	}

	public UserRole(
		Integer urId
	){
		this.urId = urId;
	}

	public void setUrId(Integer value) {
		this.urId = value;
	}
	
	public Integer getUrId() {
		return this.urId;
	}
	public void setUserId(Integer value) {
		this.userId = value;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	public void setRoleId(Integer value) {
		this.roleId = value;
	}
	
	public Integer getRoleId() {
		return this.roleId;
	}
	
	private LoginUser loginUser;
	
	public void setLoginUser(LoginUser loginUser){
		this.loginUser = loginUser;
	}
	
	public LoginUser getLoginUser() {
		return loginUser;
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
			.append(getUrId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserRole == false) return false;
		if(this == obj) return true;
		UserRole other = (UserRole)obj;
		return new EqualsBuilder()
			.append(getUrId(),other.getUrId())
			.isEquals();
	}
}

