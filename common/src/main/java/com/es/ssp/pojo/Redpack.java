package com.es.ssp.pojo;

import java.io.Serializable;

public class Redpack implements Serializable{
    private int type;
    private Long recordId;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}
