package com.es.operaterecorder.listener;

import com.es.operaterecorder.service.OperateRecordManager;
import com.es.ssp.model.OperateRecord;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "operate")
public class OperateReccorderListener {
    @Autowired
    private OperateRecordManager operateRecordManager;
    @RabbitHandler
    public void process(OperateRecord operateRecord){
        operateRecordManager.save(operateRecord);
    }
}
