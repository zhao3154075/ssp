package com.es.ssp.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.beans.Transient;
import java.io.*;
import java.util.*;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
public class LoginUser  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//columns START
    /**
     * 用户id       db_column: userId 
     */	
	private Integer userId;
    /**
     * 用户名       db_column: userName 
     */	
	private String userName;
    /**
     * 真实姓名       db_column: realName 
     */	
	private String realName;
    /**
     * 联系方式       db_column: mobile 
     */	
	private String mobile;
    /**
     * 角色类型       db_column: roleType 
     */	
	private Integer roleType;
    /**
     * 密码       db_column: psw 
     */	
	private String psw;
    /**
     * 创建时间       db_column: createTime 
     */	
	private Long createTime;
    /**
     * 状态       db_column: status 
     */	
	private Integer status;

	//columns END

	public LoginUser(){
	}

	public LoginUser(
		Integer userId
	){
		this.userId = userId;
	}

	public void setUserId(Integer value) {
		this.userId = value;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setRealName(String value) {
		this.realName = value;
	}
	
	public String getRealName() {
		return this.realName;
	}
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	public void setRoleType(Integer value) {
		this.roleType = value;
	}
	
	public Integer getRoleType() {
		return this.roleType;
	}
	public void setPsw(String value) {
		this.psw = value;
	}
	
	public String getPsw() {
		return this.psw;
	}
	public void setCreateTime(Long value) {
		this.createTime = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	private Set operateRecords = new HashSet(0);
	public void setOperateRecords(Set<OperateRecord> operateRecord){
		this.operateRecords = operateRecord;
	}
	
	public Set<OperateRecord> getOperateRecords() {
		return operateRecords;
	}
	private Set userRoles = new HashSet(0);

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	@Transient
	public Set<String> getRolesName() {
		Set<UserRole> roles = getUserRoles();
		Set<String> set = new HashSet<String>();
		for (UserRole role : roles) {
			set.add(role.getRole().getName());
		}
		return set;
	}

	@Transient
	public Set<String> getPermissionsName() {
		Set<UserRole> roles = getUserRoles();
		Set<String> set = new HashSet<String>();
		for (UserRole role : roles) {
			for(RolePermission permission : role.getRole().getRolePermissions()){
				set.add(permission.getPermission().getName());
			}
		}
		return set;
	}

	public void setUserRoles(Set<UserRole> userRole) {
		this.userRoles = userRole;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getUserId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoginUser == false) return false;
		if(this == obj) return true;
		LoginUser other = (LoginUser)obj;
		return new EqualsBuilder()
			.append(getUserId(),other.getUserId())
			.isEquals();
	}
}

