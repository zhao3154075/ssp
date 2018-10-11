package com.es.prizerecorder.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.PrizeRecordDao;
import com.es.ssp.model.PrizeRecord;
import com.es.ssp.query.PrizeRecordQuery;
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
public class PrizeRecordManager {

	@Autowired
	private PrizeRecordDao prizeRecordDao;

	/** 
	 * 创建PrizeRecord
	 **/
	public PrizeRecord save(PrizeRecord prizeRecord) {
	    this.prizeRecordDao.save(prizeRecord);
	    return prizeRecord;
	}
	
	/** 
	 * 更新PrizeRecord
	 **/	
    public PrizeRecord update(PrizeRecord prizeRecord) {
        this.prizeRecordDao.update(prizeRecord);
        return prizeRecord;
    }	
    
	/** 
	 * 删除PrizeRecord
	 **/
    public void removeById(Long id) {
        this.prizeRecordDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到PrizeRecord
	 **/    
    public PrizeRecord getById(Long id) {
        return this.prizeRecordDao.getById(id);
    }
    
	/** 
	 * 分页查询: PrizeRecord
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(PrizeRecordQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return prizeRecordDao.findPage(query);
	}
	
    
}
