package com.es.operaterecorder.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.ReportDao;
import com.es.ssp.dao.TaskStatisticsDao;
import com.es.ssp.model.ReportTask;
import com.es.ssp.model.TaskStatistics;
import com.es.ssp.query.TaskStatisticsQuery;
import common.util.DateUtils;
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
public class TaskStatisticsManager {

	@Autowired
	private TaskStatisticsDao taskStatisticsDao;
	@Autowired
	private ReportDao reportDao;

	/** 
	 * 创建TaskStatistics
	 **/
	public TaskStatistics save(TaskStatistics taskStatistics) {
	    this.taskStatisticsDao.save(taskStatistics);
	    return taskStatistics;
	}
	
	/** 
	 * 更新TaskStatistics
	 **/	
    public TaskStatistics update(TaskStatistics taskStatistics) {
        this.taskStatisticsDao.update(taskStatistics);
        return taskStatistics;
    }	
    
	/** 
	 * 删除TaskStatistics
	 **/
    public void removeById(Integer id) {
        this.taskStatisticsDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到TaskStatistics
	 **/    
    public TaskStatistics getById(Integer id) {
        return this.taskStatisticsDao.getById(id);
    }
    
	/** 
	 * 分页查询: TaskStatistics
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(TaskStatisticsQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return taskStatisticsDao.findPage(query);
	}

	public void removeByTaskId(Integer taskId){
		List<TaskStatistics> list=taskStatisticsDao.findAllByPropertys("taskId",taskId);
		if(list!=null){
			for(TaskStatistics item:list){
				taskStatisticsDao.deleteById(item.getRecordId());
			}
		}
	}
	
    public TaskStatistics getByTaskId(Integer taskId){
		List<TaskStatistics> list=taskStatisticsDao.findAllByPropertys("taskId,year",taskId, DateUtils.getNowYear());
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public void statistics(ReportTask reportTask,Integer year){
		if(reportTask!=null){
			List<Map> countList= reportDao.taskReportCount(reportTask.getRealName(),reportTask.getMobile(),year);
			TaskStatistics taskStatistics=getByTaskId(reportTask.getTaskId());
			boolean isNew=false;
			if(taskStatistics==null){
				taskStatistics=new TaskStatistics();
				taskStatistics.setTaskId(reportTask.getTaskId());
				taskStatistics.setYear(year);
				isNew=true;
			}
			for(int i=1;i<13;i++) {
				taskStatistics.setMonth(i,0);
			}
			if(countList!=null) {
				for (Map map : countList) {
					taskStatistics.setMonth(Integer.parseInt(map.get("createMonth").toString()),((Long)map.get("monthTotal")).intValue());
				}
			}
			Integer total=0;
			for(int i=1;i<13;i++){
				total+=taskStatistics.getMonth(i);
			}
			taskStatistics.setTotalNum(total);
			taskStatistics.setTotalRate((double)total*100/(reportTask.getTaskNum()*12));
			if(isNew){
				save(taskStatistics);
			}else{
				update(taskStatistics);
			}
		}
	}

	public Map townTaskCount(String town, Integer year){
		return taskStatisticsDao.townTaskCount(town,year);
	}
}
