package com.es.operaterecorder.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.ReportDao;
import com.es.ssp.model.Report;
import com.es.ssp.query.ReportQuery;
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
public class ReportManager {

	@Autowired
	private ReportDao reportDao;

	/** 
	 * 创建Report
	 **/
	public Report save(Report report) {
	    this.reportDao.save(report);
	    return report;
	}
	
	/** 
	 * 更新Report
	 **/	
    public Report update(Report report) {
        this.reportDao.update(report);
        return report;
    }	
    
	/** 
	 * 删除Report
	 **/
    public void removeById(Long id) {
        this.reportDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到Report
	 **/    
    public Report getById(Long id) {
        return this.reportDao.getById(id);
    }
    
	/** 
	 * 分页查询: Report
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(ReportQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return reportDao.findPage(query);
	}

	public Integer getTotalReport(Integer fansId){
		Long result= reportDao.count("fansId",fansId);
		return result==null?0:result.intValue();
	}

	public List<Map> getTotalStatus(Integer fansId){
		return reportDao.selectList("Report.totalStatusCount","fansId",fansId);
	}
    
}
