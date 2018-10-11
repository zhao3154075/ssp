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
	 * 根据ID得到GlobalSetting
	 **/    
    public GlobalSetting getById(Integer id) {
        return this.globalSettingDao.getById(id);
    }
    
	public GlobalSetting getGlobalSetting(){
    	List<GlobalSetting> globalSettings = globalSettingDao.findAll();
    	if(globalSettings!=null&&globalSettings.size()>0){
    		return globalSettings.get(0);
		}
		return null;
	}
    
}
