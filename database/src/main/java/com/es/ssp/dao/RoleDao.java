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
public class RoleDao extends BaseMybatisDao<Role,Integer>{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "Role";
	}
	
	public void saveOrUpdate(Role entity) {
		if(entity.getRoleId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(RoleQuery query) {
		return pageQuery(getSqlSession(),"Role.findPage",query);
	}
	

}
