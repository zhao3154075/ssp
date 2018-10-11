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
public class ReportQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 举报id */
	private Long reportId;
	/** 粉丝id（外键） */
	private Integer fansId;
	/** 创建时间 */
	private Long createTime;
	/** 发生时间 */
	private Long happenTime;
	/** 发生地点 */
	private String happenPlace;
	/** 事件描述 */
	private String eventDesc;
	/** 描述语音 */
	private String descVoice;
	/** 佐证材料图片 */
	private String descImages;
	/** 佐证材料视频 */
	private String descVideo;
	/** 状态 */
	private Integer status;
	/** 分类1 */
	private Integer reportType1;
	/** 分类2 */
	private Integer reportType2;
	/** 初次奖励状态 */
	private Integer prizeStatus1;
	/** 追加奖励状态 */
	private Integer prizeStatus2;
	/** 备注 */
	private String remark;
	/** 小编回复 */
	private String reply;

	private String openId;

	private String nickName;

	private String realName;

	private String mobile;

	private Integer roleType;

	private Long startTime;

	private Long endTime;

	private boolean byTime;

	private Integer isHide;

	public Long getReportId() {
		return this.reportId;
	}
	
	public void setReportId(Long value) {
		this.reportId = value;
	}
	
	public Integer getFansId() {
		return this.fansId;
	}
	
	public void setFansId(Integer value) {
		this.fansId = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Long value) {
		this.createTime = value;
	}
	
	public Long getHappenTime() {
		return this.happenTime;
	}
	
	public void setHappenTime(Long value) {
		this.happenTime = value;
	}
	
	public String getHappenPlace() {
		return this.happenPlace;
	}
	
	public void setHappenPlace(String value) {
		this.happenPlace = value;
	}
	
	public String getEventDesc() {
		return this.eventDesc;
	}
	
	public void setEventDesc(String value) {
		this.eventDesc = value;
	}
	
	public String getDescVoice() {
		return this.descVoice;
	}
	
	public void setDescVoice(String value) {
		this.descVoice = value;
	}
	
	public String getDescImages() {
		return this.descImages;
	}
	
	public void setDescImages(String value) {
		this.descImages = value;
	}
	
	public String getDescVideo() {
		return this.descVideo;
	}
	
	public void setDescVideo(String value) {
		this.descVideo = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getReportType1() {
		return this.reportType1;
	}
	
	public void setReportType1(Integer value) {
		this.reportType1 = value;
	}
	
	public Integer getReportType2() {
		return this.reportType2;
	}
	
	public void setReportType2(Integer value) {
		this.reportType2 = value;
	}
	
	public Integer getPrizeStatus1() {
		return this.prizeStatus1;
	}
	
	public void setPrizeStatus1(Integer value) {
		this.prizeStatus1 = value;
	}
	
	public Integer getPrizeStatus2() {
		return this.prizeStatus2;
	}
	
	public void setPrizeStatus2(Integer value) {
		this.prizeStatus2 = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getReply() {
		return this.reply;
	}
	
	public void setReply(String value) {
		this.reply = value;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public boolean isByTime() {
		return byTime;
	}

	public void setByTime(boolean byTime) {
		this.byTime = byTime;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

