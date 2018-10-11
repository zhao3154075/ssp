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
public class LoginUserDao extends BaseMybatisDao<LoginUser,Integer>{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "LoginUser";
	}
	
	public void saveOrUpdate(LoginUser entity) {
		if(entity.getUserId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(LoginUserQuery query) {
		return pageQuery(getSqlSession(),"LoginUser.findPage",query);
	}

	public LoginUser getByUserName(String userName) {
		return getByPropertys("userName",userName);
	}

}
