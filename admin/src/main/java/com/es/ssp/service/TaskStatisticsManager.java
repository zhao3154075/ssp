package com.es.ssp.service;

import common.util.CommonUtils;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.util.*;

import java.math.*;

import common.base.*;

import static common.util.DateFormats.*;
import static common.util.GlobalMessages.*;
import static common.util.SpringMVCUtils.toModelMap;
import static common.util.MybatisPageQueryUtils.pageQuery;

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;
import static cn.org.rapid_framework.beanutils.BeanUtils.copyProperties;
import static cn.org.rapid_framework.util.holder.BeanValidatorHolder.validateWithException;
import cn.org.rapid_framework.util.DateConvertUtils;

import cn.org.rapid_framework.page.PageRequest;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.*;

import com.es.ssp.model.*;
import com.es.ssp.dao.*;
import com.es.ssp.service.*;
import com.es.ssp.query.*;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class TaskStatisticsManager {

	@Autowired
	private TaskStatisticsDao taskStatisticsDao;

	/** 
	 * 创建TaskStatistics
	 **/
	public TaskStatistics save(TaskStatistics taskStatistics) {
	    this.taskStatisticsDao.save(taskStatistics);
	    return taskStatistics;
	}
	
	/** 
	 * 更新TaskStatistics
	 **/	
    public TaskStatistics update(TaskStatistics taskStatistics) {
        this.taskStatisticsDao.update(taskStatistics);
        return taskStatistics;
    }	
    
	/** 
	 * 删除TaskStatistics
	 **/
    public void removeById(Integer id) {
        this.taskStatisticsDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到TaskStatistics
	 **/    
    public TaskStatistics getById(Integer id) {
        return this.taskStatisticsDao.getById(id);
    }
    
	/** 
	 * 分页查询: TaskStatistics
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(TaskStatisticsQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return taskStatisticsDao.findPage(query);
	}

	public void export(TaskStatisticsQuery query, OutputStream out)throws Exception{
		List<TaskStatistics> list=taskStatisticsDao.findAllByPropertys("realName,mobile,workUnit,taskNum,town,community,sortColumns,year"
				,query.getRealName(),query.getMobile(),query.getWorkUnit(),query.getTaskNum(),query.getTown(),query.getCommunity(),query.getSortColumns(),query.getYear());

		WritableWorkbook wwb = Workbook.createWorkbook(out);
		WritableSheet ws = wwb.createSheet("Sheet 1", 0);
		String[] heads = new String[]{"姓名","联系电话","工作单位职务","所属镇街","所属村社","月任务数量","录入时间"};
		for (int i = 0; i < heads.length; i++) {
			ws.addCell(new Label(i, 0, heads[i]));
		}
		for(int i=1;i<13;i++){
			ws.addCell(new Label(heads.length-1+i, 0, i+"月完成数"));
		}
		ws.addCell(new Label(heads.length+12, 0, "年度完成数"));
		ws.addCell(new Label(heads.length+13, 0, "年度完成率"));
		int row = 0;
		for (TaskStatistics record:list) {
			row++;
			ws.addCell(new Label(0, row, record.getReportTask().getRealName()));
			ws.addCell(new Label(1, row, record.getReportTask().getMobile()));
			ws.addCell(new Label(2, row, record.getReportTask().getWorkUnit()));
			ws.addCell(new Label(3, row, record.getReportTask().getTown()));
			ws.addCell(new Label(4, row, record.getReportTask().getCommunity()));
			ws.addCell(new Label(5, row, record.getReportTask().getTaskNum()+""));
			ws.addCell(new Label(6, row, CommonUtils.format(record.getReportTask().getCreateTime(),"yyyy-MM-dd HH:mm:ss")));
			for(int i=1;i<13;i++){
				ws.addCell(new Label(6+i, row, record.getMonth(i)+""));
			}
			ws.addCell(new Label(19, row, record.getTotalNum()+""));
			ws.addCell(new Label(20, row, new DecimalFormat("0.0").format(record.getTotalRate())+"%"));
		}
		wwb.write();
		wwb.close();
	}
}
