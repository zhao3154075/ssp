package com.es.ssp.service;

import common.util.CommonUtils;
import common.util.MybatisPageQueryUtils;
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
public class ReportStatisticsManager {

	@Autowired
	private ReportStatisticsDao reportStatisticsDao;

	/** 
	 * 创建ReportStatistics
	 **/
	public ReportStatistics save(ReportStatistics reportStatistics) {
	    this.reportStatisticsDao.save(reportStatistics);
	    return reportStatistics;
	}
	
	/** 
	 * 更新ReportStatistics
	 **/	
    public ReportStatistics update(ReportStatistics reportStatistics) {
        this.reportStatisticsDao.update(reportStatistics);
        return reportStatistics;
    }	
    
	/** 
	 * 删除ReportStatistics
	 **/
    public void removeById(Integer id) {
        this.reportStatisticsDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到ReportStatistics
	 **/    
    public ReportStatistics getById(Integer id) {
        return this.reportStatisticsDao.getById(id);
    }
    
	/** 
	 * 分页查询: ReportStatistics
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(ReportStatisticsQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return reportStatisticsDao.findPage(query);
	}

	public void export(ReportStatisticsQuery query, OutputStream out)throws Exception{
		List<Map> list=reportStatisticsDao.getSqlSession().selectList("ReportStatistics.findAll", MybatisPageQueryUtils.toParameterMap(query));
		WritableWorkbook wwb = Workbook.createWorkbook(out);
		WritableSheet ws = wwb.createSheet("Sheet 1", 0);
		String[] heads = new String[]{"openid","微信昵称","姓名","联系电话","信息数量","待初审","待复审","初审未通过","待核实","复审未通过","待整改","信息不准确","已整改",query.getYear()+"年度贡献值","总贡献值"};
		for (int i = 0; i < heads.length; i++) {
			ws.addCell(new Label(i, 0, heads[i]));
		}
		int row = 0;
		for (Map record:list) {
			row++;
			ws.addCell(new Label(0, row, record.get("openId").toString()));
			ws.addCell(new Label(1, row, record.get("nickName").toString()));
			ws.addCell(new Label(2, row, record.get("realName").toString()));
			ws.addCell(new Label(3, row, record.get("mobile").toString()));
			ws.addCell(new Label(4, row, record.get("totalReport").toString()));
			ws.addCell(new Label(5, row, record.get("totalStatus1").toString()));
			ws.addCell(new Label(6, row, record.get("totalStatus2").toString()));
			ws.addCell(new Label(7, row, record.get("totalStatus3").toString()));
			ws.addCell(new Label(8, row, record.get("totalStatus4").toString()));
			ws.addCell(new Label(9, row, record.get("totalStatus5").toString()));
			ws.addCell(new Label(10, row, record.get("totalStatus6").toString()));
			ws.addCell(new Label(11, row, record.get("totalStatus7").toString()));
			ws.addCell(new Label(12, row, record.get("totalStatus8").toString()));
			ws.addCell(new Label(13, row, Integer.parseInt(record.get("totalAmountYear").toString())/100+""));
			ws.addCell(new Label(14, row, Integer.parseInt(record.get("totalAmount").toString())/100+""));
		}
		wwb.write();
		wwb.close();
	}
}
