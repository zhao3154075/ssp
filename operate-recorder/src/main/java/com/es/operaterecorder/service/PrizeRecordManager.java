package com.es.operaterecorder.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.PrizeRecordDao;
import com.es.ssp.model.PrizeRecord;
import com.es.ssp.query.PrizeRecordQuery;
import common.util.DateUtils;
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

	public PrizeRecord findSentRecord(Long reportId,Integer type){
		return prizeRecordDao.getByPropertys("reportId,type",reportId,type);
	}

	public Integer getTotalAmount(Integer fansId){
		Long result= prizeRecordDao.selectOne("PrizeRecord.getTotalAmount","fansId",fansId);
		return result==null?0:result.intValue();
	}

	public Integer getThisYearAmount(Integer fansId,int year){
		long startTime=DateUtils.getBeginDayOfYear(year).getTime()/1000;
		long endTime=DateUtils.getEndDayOfYear(year).getTime()/1000;
		Long result= prizeRecordDao.selectOne("PrizeRecord.getTotalAmount","fansId,startTime,endTime",fansId,startTime,endTime);
		return result==null?0:result.intValue();
	}


}
