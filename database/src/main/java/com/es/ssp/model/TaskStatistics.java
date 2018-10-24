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
public class TaskStatistics  implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TaskStatistics";
	public static final String ALIAS_RECORD_ID = "recordId";
	public static final String ALIAS_MONTH1 = "month1";
	public static final String ALIAS_MONTH2 = "month2";
	public static final String ALIAS_MONTH3 = "month3";
	public static final String ALIAS_MONTH4 = "month4";
	public static final String ALIAS_MONTH5 = "month5";
	public static final String ALIAS_MONTH6 = "month6";
	public static final String ALIAS_MONTH7 = "month7";
	public static final String ALIAS_MONTH8 = "month8";
	public static final String ALIAS_MONTH9 = "month9";
	public static final String ALIAS_MONTH10 = "month10";
	public static final String ALIAS_MONTH11 = "month11";
	public static final String ALIAS_MONTH12 = "month12";
	public static final String ALIAS_TOTAL_NUM = "totalNum";
	public static final String ALIAS_TOTAL_RATE = "totalRate";
	public static final String ALIAS_YEAR = "year";
	public static final String ALIAS_TASK_ID = "taskId";
	
	//date formats
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * recordId       db_column: recordId 
     */	
	private Integer recordId;
    /**
     * month1       db_column: month1 
     */	
	private Integer month1;
    /**
     * month2       db_column: month2 
     */	
	private Integer month2;
    /**
     * month3       db_column: month3 
     */	
	private Integer month3;
    /**
     * month4       db_column: month4 
     */	
	private Integer month4;
    /**
     * month5       db_column: month5 
     */	
	private Integer month5;
    /**
     * month6       db_column: month6 
     */	
	private Integer month6;
    /**
     * month7       db_column: month7 
     */	
	private Integer month7;
    /**
     * month8       db_column: month8 
     */	
	private Integer month8;
    /**
     * month9       db_column: month9 
     */	
	private Integer month9;
    /**
     * month10       db_column: month10 
     */	
	private Integer month10;
    /**
     * month11       db_column: month11 
     */	
	private Integer month11;
    /**
     * month12       db_column: month12 
     */	
	private Integer month12;
    /**
     * totalNum       db_column: totalNum 
     */	
	private Integer totalNum;
    /**
     * totalRate       db_column: totalRate 
     */	
	
	private Double totalRate;
    /**
     * year       db_column: year 
     */	
	private Integer year;
    /**
     * taskId       db_column: taskId 
     */	
	private Integer taskId;
	//columns END

	public TaskStatistics(){
	}

	public TaskStatistics(
		Integer recordId
	){
		this.recordId = recordId;
	}

	public void setRecordId(Integer value) {
		this.recordId = value;
	}
	
	public Integer getRecordId() {
		return this.recordId;
	}
	public void setMonth1(Integer value) {
		this.month1 = value;
	}
	
	public Integer getMonth1() {
		return this.month1;
	}
	public void setMonth2(Integer value) {
		this.month2 = value;
	}
	
	public Integer getMonth2() {
		return this.month2;
	}
	public void setMonth3(Integer value) {
		this.month3 = value;
	}
	
	public Integer getMonth3() {
		return this.month3;
	}
	public void setMonth4(Integer value) {
		this.month4 = value;
	}
	
	public Integer getMonth4() {
		return this.month4;
	}
	public void setMonth5(Integer value) {
		this.month5 = value;
	}
	
	public Integer getMonth5() {
		return this.month5;
	}
	public void setMonth6(Integer value) {
		this.month6 = value;
	}
	
	public Integer getMonth6() {
		return this.month6;
	}
	public void setMonth7(Integer value) {
		this.month7 = value;
	}
	
	public Integer getMonth7() {
		return this.month7;
	}
	public void setMonth8(Integer value) {
		this.month8 = value;
	}
	
	public Integer getMonth8() {
		return this.month8;
	}
	public void setMonth9(Integer value) {
		this.month9 = value;
	}
	
	public Integer getMonth9() {
		return this.month9;
	}
	public void setMonth10(Integer value) {
		this.month10 = value;
	}
	
	public Integer getMonth10() {
		return this.month10;
	}
	public void setMonth11(Integer value) {
		this.month11 = value;
	}
	
	public Integer getMonth11() {
		return this.month11;
	}
	public void setMonth12(Integer value) {
		this.month12 = value;
	}
	
	public Integer getMonth12() {
		return this.month12;
	}
	public void setTotalNum(Integer value) {
		this.totalNum = value;
	}
	
	public Integer getTotalNum() {
		return this.totalNum;
	}
	public void setTotalRate(Double value) {
		this.totalRate = value;
	}
	
	public Double getTotalRate() {
		return this.totalRate;
	}
	public void setYear(Integer value) {
		this.year = value;
	}
	
	public Integer getYear() {
		return this.year;
	}
	public void setTaskId(Integer value) {
		this.taskId = value;
	}
	
	public Integer getTaskId() {
		return this.taskId;
	}
	public void setMonth(Integer month,Integer value){
		try{
			this.getClass().getMethod("setMonth" + month, Integer.class).invoke(this,value);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public Integer getMonth(Integer month){
		try{
			Object o= this.getClass().getMethod("getMonth" + month).invoke(this);
			if(o!=null){
				return (Integer)o;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	private ReportTask reportTask;
	
	public void setReportTask(ReportTask reportTask){
		this.reportTask = reportTask;
	}
	
	public ReportTask getReportTask() {
		return reportTask;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRecordId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TaskStatistics == false) return false;
		if(this == obj) return true;
		TaskStatistics other = (TaskStatistics)obj;
		return new EqualsBuilder()
			.append(getRecordId(),other.getRecordId())
			.isEquals();
	}
}

