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
public class RoleManager {

	@Autowired
	private RoleDao roleDao;

	/** 
	 * 创建Role
	 **/
	public Role save(Role role) {
	    this.roleDao.save(role);
	    return role;
	}
	
	/** 
	 * 更新Role
	 **/	
    public Role update(Role role) {
        this.roleDao.update(role);
        return role;
    }	
    
	/** 
	 * 删除Role
	 **/
    public void removeById(Integer id) {
        this.roleDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到Role
	 **/    
    public Role getById(Integer id) {
        return this.roleDao.getById(id);
    }
    
	/** 
	 * 分页查询: Role
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(RoleQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return roleDao.findPage(query);
	}
	
    
}
