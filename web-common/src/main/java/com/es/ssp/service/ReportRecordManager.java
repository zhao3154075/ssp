package com.es.ssp.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportRecordManager {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 发起粉丝统计
     * @param reportId
     */
    public void reportStatistics(Long reportId){
        rabbitTemplate.convertAndSend("reportStatistics",reportId);
    }
}
