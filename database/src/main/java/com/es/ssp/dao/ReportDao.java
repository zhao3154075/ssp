package com.es.ssp.dao;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.model.Report;
import com.es.ssp.query.ReportQuery;
import common.base.BaseMybatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

import static common.util.MybatisPageQueryUtils.pageQuery;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Repository
public class ReportDao extends BaseMybatisDao<Report,Long>{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "Report";
	}
	
	public void saveOrUpdate(Report entity) {
		if(entity.getReportId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ReportQuery query) {
		return pageQuery(getSqlSession(),"Report.findPage",query);
	}

	public Page findPageOfFans(ReportQuery query){return pageQuery(getSqlSession(),"Report.findPage2",query);}

	public List<Report> findForExport(ReportQuery query){
		List list = getSqlSession().selectList("Report.findByExport",query);
		return list;
	}

}
