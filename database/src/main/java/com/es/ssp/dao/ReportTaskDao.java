package com.es.ssp.dao;

import java.io.*;
import java.net.*;
import java.util.*;

import common.base.*;

import static common.util.MybatisPageQueryUtils.pageQuery;

import cn.org.rapid_framework.page.Page;

import com.es.ssp.model.*;
import com.es.ssp.query.*;


import org.springframework.stereotype.Repository;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Repository
public class ReportTaskDao extends BaseMybatisDao<ReportTask,Integer>{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "ReportTask";
	}
	
	public void saveOrUpdate(ReportTask entity) {
		if(entity.getTaskId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ReportTaskQuery query) {
		return pageQuery(getSqlSession(),"ReportTask.findPage",query);
	}
	

}
