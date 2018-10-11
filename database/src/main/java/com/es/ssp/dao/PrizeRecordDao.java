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
public class PrizeRecordDao extends BaseMybatisDao<PrizeRecord,Long>{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "PrizeRecord";
	}
	
	public void saveOrUpdate(PrizeRecord entity) {
		if(entity.getRecordId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(PrizeRecordQuery query) {
		return pageQuery(getSqlSession(),"PrizeRecord.findPage",query);
	}
	

}
