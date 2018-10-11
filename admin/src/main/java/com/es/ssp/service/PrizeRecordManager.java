package com.es.ssp.service;

import common.util.CommonUtils;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import static common.util.SpringMVCUtils.toModelMap;
import static common.util.MybatisPageQueryUtils.pageQuery;

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;
import static cn.org.rapid_framework.beanutils.BeanUtils.copyProperties;

import cn.org.rapid_framework.page.Page;

import com.es.ssp.model.*;
import com.es.ssp.dao.*;
import com.es.ssp.query.*;

import java.io.OutputStream;
import java.net.URL;
import java.util.List;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class PrizeRecordManager {

	@Autowired
	private PrizeRecordDao prizeRecordDao;

	/** 
	 * 创建PrizeRecord
	 **/
	public PrizeRecord save(PrizeRecord prizeRecord) {
	    this.prizeRecordDao.save(prizeRecord);
	    return prizeRecord;
	}
	
	/** 
	 * 更新PrizeRecord
	 **/	
    public PrizeRecord update(PrizeRecord prizeRecord) {
        this.prizeRecordDao.update(prizeRecord);
        return prizeRecord;
    }	
    
	/** 
	 * 删除PrizeRecord
	 **/
    public void removeById(Long id) {
        this.prizeRecordDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到PrizeRecord
	 **/    
    public PrizeRecord getById(Long id) {
        return this.prizeRecordDao.getById(id);
    }
    
	/** 
	 * 分页查询: PrizeRecord
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(PrizeRecordQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return prizeRecordDao.findPage(query);
	}

	public void export(PrizeRecordQuery query, OutputStream out)throws Exception{
		List<PrizeRecord> list=prizeRecordDao.findAllByPropertys("openId,nickName,status",query.getOpenId(),query.getNickName(),query.getStatus());
		WritableWorkbook wwb = Workbook.createWorkbook(out);
		WritableSheet ws = wwb.createSheet("Sheet 1", 0);
		String[] heads = new String[]{"openid","微信昵称","发放时间","发放金额","状态","备注"};
		for (int i = 0; i < heads.length; i++) {
			ws.addCell(new Label(i, 0, heads[i]));
		}
		int row = 0;
		for (PrizeRecord record:list) {
			row++;
			ws.addCell(new Label(0, row, record.getFans().getOpenId()));
			ws.addCell(new Label(1, row, record.getFans().getNickName()));
			ws.addCell(new Label(2, row, CommonUtils.format(record.getCreateTime(),"yyyy-MM-dd HH:mm:ss")));
			ws.addCell(new Label(3, row, record.getAmount()/100+"元"));
			ws.addCell(new Label(4, row, record.getStatusStr()));
			ws.addCell(new Label(5, row, record.getErrorInfo()));
		}
		wwb.write();
		wwb.close();
	}
    
}
