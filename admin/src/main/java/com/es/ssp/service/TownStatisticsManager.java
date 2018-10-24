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
public class TownStatisticsManager {

	@Autowired
	private TownStatisticsDao townStatisticsDao;

	/** 
	 * 创建TownStatistics
	 **/
	public TownStatistics save(TownStatistics townStatistics) {
	    this.townStatisticsDao.save(townStatistics);
	    return townStatistics;
	}
	
	/** 
	 * 更新TownStatistics
	 **/	
    public TownStatistics update(TownStatistics townStatistics) {
        this.townStatisticsDao.update(townStatistics);
        return townStatistics;
    }	
    
	/** 
	 * 删除TownStatistics
	 **/
    public void removeById(Integer id) {
        this.townStatisticsDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到TownStatistics
	 **/    
    public TownStatistics getById(Integer id) {
        return this.townStatisticsDao.getById(id);
    }
    
	/** 
	 * 分页查询: TownStatistics
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(TownStatisticsQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return townStatisticsDao.findPage(query);
	}

	public void export(TownStatisticsQuery query, OutputStream out)throws Exception{
		List<TownStatistics> list=townStatisticsDao.findAllByPropertys("town,sortColumns,year"
				,query.getTown(),query.getSortColumns(),query.getYear());

		WritableWorkbook wwb = Workbook.createWorkbook(out);
		WritableSheet ws = wwb.createSheet("Sheet 1", 0);
		String[] heads = new String[]{"镇街名称","志愿者数量"};
		for (int i = 0; i < heads.length; i++) {
			ws.addCell(new Label(i, 0, heads[i]));
		}
		for(int i=1;i<13;i++){
			ws.addCell(new Label(heads.length-1+i, 0, i+"月完成数"));
		}
		ws.addCell(new Label(heads.length+12, 0, "年度完成数"));
		ws.addCell(new Label(heads.length+13, 0, "年度完成率"));
		int row = 0;
		for (TownStatistics record:list) {
			row++;
			ws.addCell(new Label(0, row, record.getTown()));
			ws.addCell(new Label(1, row, record.getTotalVolunteer()+""));
			for(int i=1;i<13;i++){
				ws.addCell(new Label(1+i, row, record.getMonth(i)+""));
			}
			ws.addCell(new Label(14, row, record.getTotalNum()+""));
			ws.addCell(new Label(15, row, new DecimalFormat("0.0").format(record.getTotalRate())+"%"));
		}
		wwb.write();
		wwb.close();
	}
}
