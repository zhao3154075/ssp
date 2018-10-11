package com.es.ssp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import static common.util.SpringMVCUtils.toModelMap;
import static common.util.MybatisPageQueryUtils.pageQuery;

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;
import static cn.org.rapid_framework.beanutils.BeanUtils.copyProperties;

import cn.org.rapid_framework.page.Page;

import com.es.ssp.model.*;
import com.es.ssp.dao.*;
import com.es.ssp.query.*;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class LoginUserManager {

	@Autowired
	private LoginUserDao loginUserDao;

	/** 
	 * 创建LoginUser
	 **/
	public LoginUser save(LoginUser loginUser) {
	    this.loginUserDao.save(loginUser);
	    return loginUser;
	}
	
	/** 
	 * 更新LoginUser
	 **/	
    public LoginUser update(LoginUser loginUser) {
        this.loginUserDao.update(loginUser);
        return loginUser;
    }	
    
	/** 
	 * 删除LoginUser
	 **/
    public void removeById(Integer id) {
        this.loginUserDao.deleteById(id);
    }
    
	/** 
	 * 根据UserName得到LoginUser
	 **/    
    public LoginUser getByUserName(String userName) {
        return this.loginUserDao.getByUserName(userName);
    }

	/**
	 * 根据ID得到LoginUser
	 **/
	public LoginUser getById(Integer id) {
		return this.loginUserDao.getById(id);
	}


    
	/** 
	 * 分页查询: LoginUser
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(LoginUserQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return loginUserDao.findPage(query);
	}
	
    
}
