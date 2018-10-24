package com.es.operaterecorder.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.ReportDao;
import com.es.ssp.dao.ReportTaskDao;
import com.es.ssp.model.ReportTask;
import com.es.ssp.query.ReportTaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ReportTaskManager {

	@Autowired
	private ReportTaskDao reportTaskDao;
	@Autowired
	private ReportDao reportDao;

	/** 
	 * 创建ReportTask
	 **/
	public ReportTask save(ReportTask reportTask) {
	    this.reportTaskDao.save(reportTask);
	    return reportTask;
	}
	
	/** 
	 * 更新ReportTask
	 **/	
    public ReportTask update(ReportTask reportTask) {
        this.reportTaskDao.update(reportTask);
        return reportTask;
    }

	/**
	 * 删除ReportTask
	 **/
	public void removeById(Integer id) {
		this.reportTaskDao.deleteById(id);
	}

	/** 
	 * 根据ID得到ReportTask
	 **/    
    public ReportTask getById(Integer id) {
        return this.reportTaskDao.getById(id);
    }

    public ReportTask getByNameAndMobile(String realName,String mobile){
    	List<ReportTask> reportTasks=reportTaskDao.findAllByPropertys("realName,mobile",realName,mobile);
    	if(reportTasks!=null&&reportTasks.size()>0){
    		return reportTasks.get(0);
		}
		return null;
	}

}
