package com.es.ssp.dao;


import common.base.*;

import static common.util.MybatisPageQueryUtils.pageQuery;

import cn.org.rapid_framework.page.Page;

import com.es.ssp.model.*;
import com.es.ssp.query.*;


import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Repository
public class UserRoleDao extends BaseMybatisDao<UserRole,Integer>{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "UserRole";
	}
	
	public void saveOrUpdate(UserRole entity) {
		if(entity.getUrId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(UserRoleQuery query) {
		return pageQuery(getSqlSession(),"UserRole.findPage",query);
	}

	public List<UserRole> findUserRoles(Integer userId){
		return findAllByPropertys("userId",userId);
	}
}
