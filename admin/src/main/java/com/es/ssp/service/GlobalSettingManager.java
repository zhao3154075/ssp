package com.es.ssp.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.GlobalSettingDao;
import com.es.ssp.model.GlobalSetting;
import com.es.ssp.query.GlobalSettingQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class GlobalSettingManager {

	@Autowired
	private GlobalSettingDao globalSettingDao;

	/** 
	 * 创建GlobalSetting
	 **/
	public GlobalSetting save(GlobalSetting globalSetting) {
	    this.globalSettingDao.save(globalSetting);
	    return globalSetting;
	}
	
	/** 
	 * 更新GlobalSetting
	 **/	
    public GlobalSetting update(GlobalSetting globalSetting) {
        this.globalSettingDao.update(globalSetting);
        return globalSetting;
    }	
    
	/** 
	 * 删除GlobalSetting
	 **/
    public void removeById(Integer id) {
        this.globalSettingDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到GlobalSetting
	 **/    
    public GlobalSetting getById(Integer id) {
        return this.globalSettingDao.getById(id);
    }
    
	/** 
	 * 分页查询: GlobalSetting
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(GlobalSettingQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return globalSettingDao.findPage(query);
	}

	public GlobalSetting getOne(){
		List<GlobalSetting> list = globalSettingDao.findAll();
		if (list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
