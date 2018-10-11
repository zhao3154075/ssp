package com.es.ssp.service;

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

	/**
	 * 获取当天已领取次数
	 * @param fansId
	 * @return
	 */
	public Long getDayTotalCount(Integer fansId,Integer type){
		long startTime=DateUtils.getTimesmorning(0).getTime()/1000;
		long endTime=DateUtils.getTimesmorning(1).getTime()/1000;
		Long result= prizeRecordDao.selectOne("PrizeRecord.getTotalCount","fansId,type,startTime,endTime",fansId,type,startTime,endTime);
		return result==null?0L:result;
	}
}
