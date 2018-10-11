package com.es.operaterecorder.service;

import com.es.ssp.dao.OperateRecordDao;
import com.es.ssp.model.OperateRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

	/** 
	 * 创建OperateRecord
	 **/
	public OperateRecord save(OperateRecord operateRecord) {
	    this.operateRecordDao.save(operateRecord);
	    return operateRecord;
	}

}
