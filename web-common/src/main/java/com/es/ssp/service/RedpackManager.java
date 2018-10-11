package com.es.ssp.service;

import com.es.ssp.pojo.Redpack;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedpackManager {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 发送红包
     * @param recordId 发送记录id
     */
    public void sendRedpack(Long recordId){
        Redpack redpack=new Redpack();
        redpack.setType(0);
        redpack.setRecordId(recordId);
        rabbitTemplate.convertAndSend("redpack",redpack);
    }

    /**
     * 重发红包
     * @param recordId 发送记录id
     */
    public void resentRedpack(Long recordId){
        Redpack redpack=new Redpack();
        redpack.setType(1);
        redpack.setRecordId(recordId);
        rabbitTemplate.convertAndSend("redpack",redpack);
    }
}
