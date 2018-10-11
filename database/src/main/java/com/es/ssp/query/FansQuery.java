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
public class FansQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 粉丝ID */
	private Integer fansId;
	/** openId */
	private String openId;
	/** 昵称 */
	private String nickName;
	/** 姓名 */
	private String realName;
	/** 联系方式 */
	private String mobile;
	/** 创建时间 */
	private Long createTime;
	/**
	 * 头像
	 */
	private String headimgurl;
	/**
	 * 是否关注
	 */
	private Integer subscribe;

	public Integer getFansId() {
		return this.fansId;
	}
	
	public void setFansId(Integer value) {
		this.fansId = value;
	}
	
	public String getOpenId() {
		return this.openId;
	}
	
	public void setOpenId(String value) {
		this.openId = value;
	}
	
	public String getNickName() {
		return this.nickName;
	}
	
	public void setNickName(String value) {
		this.nickName = value;
	}
	
	public String getRealName() {
		return this.realName;
	}
	
	public void setRealName(String value) {
		this.realName = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Long value) {
		this.createTime = value;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

