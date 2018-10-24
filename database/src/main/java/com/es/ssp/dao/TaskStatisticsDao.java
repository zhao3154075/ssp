package com.es.ssp.dao;
import common.base.*;
import static common.util.MybatisPageQueryUtils.pageQuery;
import cn.org.rapid_framework.page.Page;

import com.es.ssp.model.*;
import com.es.ssp.query.*;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Repository
public class TaskStatisticsDao extends BaseMybatisDao<TaskStatistics,Integer>{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "TaskStatistics";
	}
	
	public void saveOrUpdate(TaskStatistics entity) {
		if(entity.getRecordId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(TaskStatisticsQuery query) {
		return pageQuery(getSqlSession(),"TaskStatistics.findPage",query);
	}

	/**
	 * 统计年度镇街任务完成情况
	 * @param town
	 * @param year
	 * @return
	 */
	public Map townTaskCount(String town,Integer year){
		return selectOne("TaskStatistics.townTaskCount","town,year",town,year);
	}

}
