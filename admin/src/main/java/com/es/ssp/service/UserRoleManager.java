package com.es.ssp.service;

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
public class UserRoleManager {

	@Autowired
	private UserRoleDao userRoleDao;

	/** 
	 * 创建UserRole
	 **/
	public UserRole save(UserRole userRole) {
	    this.userRoleDao.save(userRole);
	    return userRole;
	}
	
	/** 
	 * 更新UserRole
	 **/	
    public UserRole update(UserRole userRole) {
        this.userRoleDao.update(userRole);
        return userRole;
    }	
    
	/** 
	 * 删除UserRole
	 **/
    public void removeById(Integer id) {
        this.userRoleDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到UserRole
	 **/    
    public UserRole getById(Integer id) {
        return this.userRoleDao.getById(id);
    }


	/** 
	 * 分页查询: UserRole
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(UserRoleQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return userRoleDao.findPage(query);
	}

	public List<UserRole> findUserRoles(Integer userId){
		return userRoleDao.findUserRoles(userId);
	}
	
    
}
