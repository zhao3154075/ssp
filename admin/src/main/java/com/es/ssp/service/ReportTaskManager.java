package com.es.ssp.service;

import org.springframework.amqp.core.AmqpTemplate;
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
public class ReportTaskManager {

	@Autowired
	private ReportTaskDao reportTaskDao;
	@Autowired
	private AmqpTemplate rabbitTemplate;
	/** 
	 * 创建ReportTask
	 **/
	public ReportTask save(ReportTask reportTask) {
	    this.reportTaskDao.save(reportTask);
	    return reportTask;
	}
	
	/** 
	 * 更新ReportTask
	 **/	
    public ReportTask update(ReportTask reportTask) {
        this.reportTaskDao.update(reportTask);
        return reportTask;
    }	
    
	/** 
	 * 删除ReportTask
	 **/
    public void removeById(Integer id) {
        this.reportTaskDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到ReportTask
	 **/    
    public ReportTask getById(Integer id) {
        return this.reportTaskDao.getById(id);
    }
    
	/** 
	 * 分页查询: ReportTask
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(ReportTaskQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return reportTaskDao.findPage(query);
	}

	public void statistics(Integer type,Long id){
		Map map=new HashMap();
		map.put("type",type);
		map.put("value",id);
		rabbitTemplate.convertAndSend("taskStatistics",map);
	}
    
}
