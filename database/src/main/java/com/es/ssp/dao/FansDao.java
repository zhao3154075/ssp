package com.es.ssp.dao;


import common.base.*;

import cn.org.rapid_framework.page.Page;

import com.es.ssp.model.*;
import com.es.ssp.query.*;


import org.springframework.stereotype.Repository;

import static common.util.MybatisPageQueryUtils.pageQuery;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Repository
public class FansDao extends BaseMybatisDao<Fans,Integer>{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "Fans";
	}
	
	@Override
    public void saveOrUpdate(Fans entity) {
		if(entity.getFansId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(FansQuery query) {
		return pageQuery(getSqlSession(),"Fans.findPage",query);
	}
	

}
