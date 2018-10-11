package com.es.ssp.dao;

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
public class OperateRecordDao extends BaseMybatisDao<OperateRecord,Long>{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "OperateRecord";
	}
	
	public void saveOrUpdate(OperateRecord entity) {
		if(entity.getRecordId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(OperateRecordQuery query) {
		return pageQuery(getSqlSession(),"OperateRecord.findPage",query);
	}
	

}
