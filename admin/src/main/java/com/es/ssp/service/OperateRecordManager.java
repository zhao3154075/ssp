package com.es.ssp.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.OperateRecordDao;
import com.es.ssp.model.OperateRecord;
import com.es.ssp.query.OperateRecordQuery;
import common.util.CommonUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class OperateRecordManager {

	@Autowired
	private OperateRecordDao operateRecordDao;
	@Autowired
	private AmqpTemplate rabbitTemplate;

	/** 
	 * 创建OperateRecord
	 **/
	public OperateRecord save(OperateRecord operateRecord) {
	    this.operateRecordDao.save(operateRecord);
	    return operateRecord;
	}
	
	/** 
	 * 更新OperateRecord
	 **/	
    public OperateRecord update(OperateRecord operateRecord) {
        this.operateRecordDao.update(operateRecord);
        return operateRecord;
    }	
    
	/** 
	 * 删除OperateRecord
	 **/
    public void removeById(Long id) {
        this.operateRecordDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到OperateRecord
	 **/    
    public OperateRecord getById(Long id) {
        return this.operateRecordDao.getById(id);
    }
    
	/** 
	 * 分页查询: OperateRecord
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(OperateRecordQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		Page page = operateRecordDao.findPage(query);
		List<OperateRecord> list = new ArrayList<>();
		List<OperateRecord> recordList = page.getResult();
		for (OperateRecord record:recordList){
			record.setDesc(CommonUtils.replaceStatus(record.getDesc()));
		}
		return page;
	}

	/**
	 * 创建操作记录
	 * @param userId
	 * @param reportId
	 * @param desc
	 */
    public void create(Integer userId,Long reportId,String desc){
		OperateRecord operateRecord=new OperateRecord();
		operateRecord.setCreateTime(System.currentTimeMillis());
		operateRecord.setDesc(desc);
		operateRecord.setReportId(reportId);
		operateRecord.setUserId(userId);
		rabbitTemplate.convertAndSend("operate",operateRecord);
	}
}
