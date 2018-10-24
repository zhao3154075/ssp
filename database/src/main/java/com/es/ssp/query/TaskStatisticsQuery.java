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
public class TaskStatisticsQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** recordId */
	private Integer recordId;
	/** month1 */
	private Integer month1;
	/** month2 */
	private Integer month2;
	/** month3 */
	private Integer month3;
	/** month4 */
	private Integer month4;
	/** month5 */
	private Integer month5;
	/** month6 */
	private Integer month6;
	/** month7 */
	private Integer month7;
	/** month8 */
	private Integer month8;
	/** month9 */
	private Integer month9;
	/** month10 */
	private Integer month10;
	/** month11 */
	private Integer month11;
	/** month12 */
	private Integer month12;
	/** totalNum */
	private Integer totalNum;
	/** totalRate */
	private Double totalRate;
	/** year */
	private Integer year;
	/** taskId */
	private Integer taskId;
	/** realName */
	private String realName;
	/** mobile */
	private String mobile;
	/** workUnit */
	private String workUnit;
	/** town */
	private String town;
	/** community */
	private String community;
	/** taskNum */
	private Integer taskNum;

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

	public String getWorkUnit() {
		return this.workUnit;
	}

	public void setWorkUnit(String value) {
		this.workUnit = value;
	}

	public String getTown() {
		return this.town;
	}

	public void setTown(String value) {
		this.town = value;
	}

	public String getCommunity() {
		return this.community;
	}

	public void setCommunity(String value) {
		this.community = value;
	}

	public Integer getTaskNum() {
		return this.taskNum;
	}

	public void setTaskNum(Integer value) {
		this.taskNum = value;
	}

	public Integer getRecordId() {
		return this.recordId;
	}
	
	public void setRecordId(Integer value) {
		this.recordId = value;
	}
	
	public Integer getMonth1() {
		return this.month1;
	}
	
	public void setMonth1(Integer value) {
		this.month1 = value;
	}
	
	public Integer getMonth2() {
		return this.month2;
	}
	
	public void setMonth2(Integer value) {
		this.month2 = value;
	}
	
	public Integer getMonth3() {
		return this.month3;
	}
	
	public void setMonth3(Integer value) {
		this.month3 = value;
	}
	
	public Integer getMonth4() {
		return this.month4;
	}
	
	public void setMonth4(Integer value) {
		this.month4 = value;
	}
	
	public Integer getMonth5() {
		return this.month5;
	}
	
	public void setMonth5(Integer value) {
		this.month5 = value;
	}
	
	public Integer getMonth6() {
		return this.month6;
	}
	
	public void setMonth6(Integer value) {
		this.month6 = value;
	}
	
	public Integer getMonth7() {
		return this.month7;
	}
	
	public void setMonth7(Integer value) {
		this.month7 = value;
	}
	
	public Integer getMonth8() {
		return this.month8;
	}
	
	public void setMonth8(Integer value) {
		this.month8 = value;
	}
	
	public Integer getMonth9() {
		return this.month9;
	}
	
	public void setMonth9(Integer value) {
		this.month9 = value;
	}
	
	public Integer getMonth10() {
		return this.month10;
	}
	
	public void setMonth10(Integer value) {
		this.month10 = value;
	}
	
	public Integer getMonth11() {
		return this.month11;
	}
	
	public void setMonth11(Integer value) {
		this.month11 = value;
	}
	
	public Integer getMonth12() {
		return this.month12;
	}
	
	public void setMonth12(Integer value) {
		this.month12 = value;
	}
	
	public Integer getTotalNum() {
		return this.totalNum;
	}
	
	public void setTotalNum(Integer value) {
		this.totalNum = value;
	}
	
	public Double getTotalRate() {
		return this.totalRate;
	}
	
	public void setTotalRate(Double value) {
		this.totalRate = value;
	}
	
	public Integer getYear() {
		return this.year;
	}
	
	public void setYear(Integer value) {
		this.year = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

