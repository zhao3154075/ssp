package com.es.operaterecorder.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.TaskStatisticsDao;
import com.es.ssp.dao.TownStatisticsDao;
import com.es.ssp.model.TownStatistics;
import com.es.ssp.query.TownStatisticsQuery;
import common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class TownStatisticsManager {

	@Autowired
	private TownStatisticsDao townStatisticsDao;
	@Autowired
	private TaskStatisticsDao taskStatisticsDao;

	/** 
	 * 创建TownStatistics
	 **/
	public TownStatistics save(TownStatistics townStatistics) {
	    this.townStatisticsDao.save(townStatistics);
	    return townStatistics;
	}
	
	/** 
	 * 更新TownStatistics
	 **/	
    public TownStatistics update(TownStatistics townStatistics) {
        this.townStatisticsDao.update(townStatistics);
        return townStatistics;
    }

    public void statistics(String town,Integer year){
    	if(StringUtils.isEmpty(town)){return;}
		TownStatistics townStatistics=getByTown(town,year);
		boolean isNew=false;
		if(townStatistics==null){
			townStatistics=new TownStatistics();
			townStatistics.setTown(town);
			townStatistics.setYear(year);
			isNew=true;
		}
		Map townCount=taskStatisticsDao.townTaskCount(town,year);
		for(int i=1;i<13;i++) {
			townStatistics.setMonth(i,0);
		}
		if(townCount!=null){
			for(int i=1;i<13;i++) {
				Object month=townCount.get("month"+i);
				if(month!=null){
					townStatistics.setMonth(i,Integer.parseInt(month.toString()));
				}
			}
            townStatistics.setTotalVolunteer(Integer.parseInt(townCount.get("totalVolunteer").toString()));
            if(townStatistics.getTotalVolunteer()==0){
                townStatisticsDao.deleteById(townStatistics.getRecordId());
                return;
            }
			townStatistics.setTotalNum(Integer.parseInt(townCount.get("totalNum").toString()));
			townStatistics.setTotalRate((double)townStatistics.getTotalNum()*100/(Integer.parseInt(townCount.get("taskNum").toString())*12));
		}
		if(isNew){
			save(townStatistics);
		}else{
            update(townStatistics);
		}
	}

    public TownStatistics getByTown(String town,Integer year){
    	List<TownStatistics> list=townStatisticsDao.findAllByPropertys("town,year",town, year);
    	if(list!=null&&list.size()>0){
    		return list.get(0);
		}
		return null;
	}
    
}
