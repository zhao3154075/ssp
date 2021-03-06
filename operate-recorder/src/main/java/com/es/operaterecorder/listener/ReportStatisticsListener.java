package com.es.operaterecorder.listener;

import com.es.operaterecorder.service.PrizeRecordManager;
import com.es.operaterecorder.service.ReportManager;
import com.es.operaterecorder.service.ReportStatisticsManager;
import com.es.operaterecorder.service.ReportStatisticsYearManager;
import com.es.ssp.model.Report;
import com.es.ssp.model.ReportStatistics;
import com.es.ssp.model.ReportStatisticsYear;
import common.util.DateUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RabbitListener(queues = "reportStatistics")
public class ReportStatisticsListener {
    @Autowired
    private ReportManager reportManager;
    @Autowired
    private PrizeRecordManager prizeRecordManager;
    @Autowired
    private ReportStatisticsManager reportStatisticsManager;
    @Autowired
    private ReportStatisticsYearManager reportStatisticsYearManager;
    @RabbitHandler
    public void process(Long reportId){
        int method=0;
        Report report=reportManager.getById(reportId);
        if(report==null){
            return;
        }
        Integer fansId=report.getFansId();
        int year=DateUtils.getNowYear(report.getCreateTime()*1000);
        ReportStatistics reportStatistics=reportStatisticsManager.getByFansId(fansId,year);
        if(reportStatistics==null){
            reportStatistics=new ReportStatistics();
            reportStatistics.setFansId(fansId);
            reportStatistics.setYear(DateUtils.getNowYear());
            method=1;
        }
        reportStatistics.setTotalReport(reportManager.getYearTotalReport(fansId,year));
        reportStatistics.setTotalStatus1(0);
        reportStatistics.setTotalStatus2(0);
        reportStatistics.setTotalStatus3(0);
        reportStatistics.setTotalStatus4(0);
        reportStatistics.setTotalStatus5(0);
        reportStatistics.setTotalStatus6(0);
        reportStatistics.setTotalStatus7(0);
        reportStatistics.setTotalStatus8(0);
        reportStatistics.setUpdateTime(System.currentTimeMillis()/1000);
        List<Map> status=reportManager.getYearTotalStatus(fansId,year);
        if(status!=null){
            for(Map item:status){
                if(item.get("status").equals(0)){
                    reportStatistics.setTotalStatus1(Integer.parseInt(item.get("total").toString()));
                }
                if(item.get("status").equals(1)){
                    reportStatistics.setTotalStatus2(Integer.parseInt(item.get("total").toString()));
                }
                if(item.get("status").equals(2)){
                    reportStatistics.setTotalStatus3(Integer.parseInt(item.get("total").toString()));
                }
                if(item.get("status").equals(3)){
                    reportStatistics.setTotalStatus4(Integer.parseInt(item.get("total").toString()));
                }
                if(item.get("status").equals(4)){
                    reportStatistics.setTotalStatus5(Integer.parseInt(item.get("total").toString()));
                }
                if(item.get("status").equals(5)){
                    reportStatistics.setTotalStatus6(Integer.parseInt(item.get("total").toString()));
                }
                if(item.get("status").equals(6)){
                    reportStatistics.setTotalStatus7(Integer.parseInt(item.get("total").toString()));
                }
                if(item.get("status").equals(7)){
                    reportStatistics.setTotalStatus8(Integer.parseInt(item.get("total").toString()));
                }
            }
        }
        reportStatistics.setTotalAmount(reportManager.getThisYearAmount(fansId,year));
        if(method==1){
            reportStatisticsManager.save(reportStatistics);
        }else{
            reportStatisticsManager.update(reportStatistics);
        }
        method=0;
        ReportStatisticsYear reportStatisticsYear=reportStatisticsYearManager.getByFansId(fansId);
        if(reportStatisticsYear==null){
            reportStatisticsYear=new ReportStatisticsYear();
            reportStatisticsYear.setFansId(fansId);
            method=1;
        }
        reportStatisticsYear.setTotalAmount(reportManager.getTotalAmount(fansId));
        if(method==1){
            reportStatisticsYearManager.save(reportStatisticsYear);
        }else{
            reportStatisticsYearManager.update(reportStatisticsYear);
        }

    }
}
