package com.es.prizerecorder.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.FansDao;
import com.es.ssp.model.Fans;
import com.es.ssp.query.FansQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class FansManager {

	@Autowired
	private FansDao fansDao;

	/** 
	 * 创建Fans
	 **/
	public Fans save(Fans fans) {
	    this.fansDao.save(fans);
	    return fans;
	}
	
	/** 
	 * 更新Fans
	 **/	
    public Fans update(Fans fans) {
        this.fansDao.update(fans);
        return fans;
    }	
    
	/** 
	 * 删除Fans
	 **/
    public void removeById(Integer id) {
        this.fansDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到Fans
	 **/    
    public Fans getById(Integer id) {
        return this.fansDao.getById(id);
    }
    
	/** 
	 * 分页查询: Fans
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(FansQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return fansDao.findPage(query);
	}
	
    
}
