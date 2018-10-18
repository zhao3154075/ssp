package com.es.operaterecorder.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.ReportStatisticsDao;
import com.es.ssp.model.ReportStatistics;
import com.es.ssp.query.ReportStatisticsQuery;
import common.util.DateUtils;
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
public class ReportStatisticsManager {

	@Autowired
	private ReportStatisticsDao reportStatisticsDao;

	/** 
	 * 创建ReportStatistics
	 **/
	public ReportStatistics save(ReportStatistics reportStatistics) {
	    this.reportStatisticsDao.save(reportStatistics);
	    return reportStatistics;
	}
	
	/** 
	 * 更新ReportStatistics
	 **/	
    public ReportStatistics update(ReportStatistics reportStatistics) {
        this.reportStatisticsDao.update(reportStatistics);
        return reportStatistics;
    }	
    
	/** 
	 * 删除ReportStatistics
	 **/
    public void removeById(Integer id) {
        this.reportStatisticsDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到ReportStatistics
	 **/    
    public ReportStatistics getById(Integer id) {
        return this.reportStatisticsDao.getById(id);
    }
    
	/** 
	 * 分页查询: ReportStatistics
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(ReportStatisticsQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return reportStatisticsDao.findPage(query);
	}

	public ReportStatistics getByFansId(Integer fansId,int year){
		List<ReportStatistics> list=reportStatisticsDao.selectList("ReportStatistics.findByFansId","id,year",fansId, year);
		if(list!=null){
			for(ReportStatistics result:list){
				return result;
			}
		}
		return null;
	}
    
}
