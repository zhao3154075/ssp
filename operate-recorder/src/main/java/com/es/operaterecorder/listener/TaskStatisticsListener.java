package com.es.operaterecorder.listener;

import com.es.operaterecorder.service.ReportManager;
import com.es.operaterecorder.service.ReportTaskManager;
import com.es.operaterecorder.service.TaskStatisticsManager;
import com.es.operaterecorder.service.TownStatisticsManager;
import com.es.ssp.model.*;
import common.util.DateUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "taskStatistics")
public class TaskStatisticsListener {
    @Autowired
    private ReportTaskManager reportTaskManager;
    @Autowired
    private TaskStatisticsManager taskStatisticsManager;
    @Autowired
    private TownStatisticsManager townStatisticsManager;
    @Autowired
    private ReportManager reportManager;
    @RabbitHandler
    public void process(Map param){
        int type=(Integer)param.get("type");
        Long value=(Long)param.get("value");
        switch (type){
            case 0:{
                ReportTask reportTask=reportTaskManager.getById(value.intValue());
                if(reportTask!=null){
                    taskStatisticsManager.statistics(reportTask,DateUtils.getNowYear());
                    townStatisticsManager.statistics(reportTask.getTown(),DateUtils.getNowYear());
                }
                break;
            }
            case 1:{
                ReportTask reportTask=reportTaskManager.getById(value.intValue());
                if(reportTask!=null){
                    taskStatisticsManager.removeByTaskId(reportTask.getTaskId());
                    reportTaskManager.removeById(reportTask.getTaskId());
                    townStatisticsManager.statistics(reportTask.getTown(),DateUtils.getNowYear());
                }
                break;
            }
            case 2:{
                Report report=reportManager.getById(value);
                if(report!=null){
                    Fans fans=report.getFans();
                    ReportTask reportTask=reportTaskManager.getByNameAndMobile(fans.getRealName(),fans.getMobile());
                    if(reportTask!=null){
                        Integer year=DateUtils.getNowYear(report.getCreateTime()*1000);
                        taskStatisticsManager.statistics(reportTask,year);
                        townStatisticsManager.statistics(reportTask.getTown(),year);
                    }
                }
                break;
            }
            default:{}
        }
    }
}
