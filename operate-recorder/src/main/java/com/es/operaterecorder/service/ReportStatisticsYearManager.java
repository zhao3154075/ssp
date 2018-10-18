package com.es.operaterecorder.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.ReportStatisticsYearDao;
import com.es.ssp.model.ReportStatisticsYear;
import com.es.ssp.query.ReportStatisticsYearQuery;
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
public class ReportStatisticsYearManager {

	@Autowired
	private ReportStatisticsYearDao reportStatisticsYearDao;

	/** 
	 * 创建ReportStatisticsYear
	 **/
	public ReportStatisticsYear save(ReportStatisticsYear reportStatisticsYear) {
	    this.reportStatisticsYearDao.save(reportStatisticsYear);
	    return reportStatisticsYear;
	}
	
	/** 
	 * 更新ReportStatisticsYear
	 **/	
    public ReportStatisticsYear update(ReportStatisticsYear reportStatisticsYear) {
        this.reportStatisticsYearDao.update(reportStatisticsYear);
        return reportStatisticsYear;
    }	
    
	/** 
	 * 删除ReportStatisticsYear
	 **/
    public void removeById(Integer id) {
        this.reportStatisticsYearDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到ReportStatisticsYear
	 **/    
    public ReportStatisticsYear getById(Integer id) {
        return this.reportStatisticsYearDao.getById(id);
    }
    
	/** 
	 * 分页查询: ReportStatisticsYear
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(ReportStatisticsYearQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return reportStatisticsYearDao.findPage(query);
	}

	public ReportStatisticsYear getByFansId(Integer fansId){
		List<ReportStatisticsYear> list=reportStatisticsYearDao.findAllByPropertys("fansId",fansId);
		if(list!=null){
			for(ReportStatisticsYear result:list){
				return result;
			}
		}
		return null;
	}
    
}
