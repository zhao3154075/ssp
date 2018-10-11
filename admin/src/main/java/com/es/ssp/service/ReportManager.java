package com.es.ssp.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.ReportDao;
import com.es.ssp.model.Report;
import com.es.ssp.query.ReportQuery;
import common.util.CommonUtils;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
public class ReportManager {

	@Autowired
	private ReportDao reportDao;
	@Value("${web.urlPrefix}")
	private String urlPrefix;

	/** 
	 * 创建Report
	 **/
	public Report save(Report report) {
	    this.reportDao.save(report);
	    return report;
	}
	
	/** 
	 * 更新Report
	 **/	
    public Report update(Report report) {
        this.reportDao.update(report);
        return report;
    }	
    
	/** 
	 * 删除Report
	 **/
    public void removeById(Long id) {
        this.reportDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到Report
	 **/    
    public Report getById(Long id) {
        return this.reportDao.getById(id);
    }
    
	/** 
	 * 分页查询: Report
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(ReportQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return reportDao.findPage(query);
	}

	@Transactional(readOnly=true)
	public Page findPageOfFans(ReportQuery query) {
		Assert.notNull(query,"'query' must be not null");
		return reportDao.findPageOfFans(query);
	}

	@Transactional(readOnly = true)
	public List<Report> findForExport(ReportQuery query){
		Assert.notNull(query,"'query' must be not null");
		return reportDao.findForExport(query);
	}

	public void export(String[] heads, List<Report> results, OutputStream out) throws Exception {
		WritableWorkbook wwb = Workbook.createWorkbook(out);
		WritableSheet ws = wwb.createSheet("Sheet 1", 0);
		if (heads != null) {
			for (int i = 0; i < heads.length; i++) {
				ws.addCell(new Label(i, 0, heads[i]));
			}
		}
		int row = 0;
		int sr = 0;
		for (Report report:results) {
			row++;
			sr = row;
			String[] imgs = null;
			if (StringUtils.isNotBlank(report.getDescImages())){
				imgs = report.getDescImages().split(",");
			}

			ws.addCell(new Label(0, row, report.getFans().getOpenId()));
			ws.addCell(new Label(1, row, report.getFans().getNickName()));
			ws.addCell(new Label(2, row, CommonUtils.format(report.getCreateTime(),"yyyy-MM-dd HH:mm:ss")));
			ws.addCell(new Label(3, row, report.getFans().getRealName()));
			ws.addCell(new Label(4, row, report.getFans().getMobile()));
			ws.addCell(new Label(5, row, CommonUtils.format(report.getHappenTime(),"yyyy-MM-dd HH:mm:ss")));
			ws.addCell(new Label(6, row, report.getHappenPlace()));
			ws.addCell(new Label(7, row, report.getEventDesc()));
			ws.addCell(new Label(8, row, report.getDescVoice()));
			ws.addCell(new Label(9, row, report.getDescImages()));
			ws.addCell(new Label(10, row, report.getDescVideo()));
			ws.addCell(new Label(11, row, report.getStatusString()));
			if (StringUtils.isNotBlank(report.getDescVoice())){
				WritableHyperlink hy = new WritableHyperlink(8,row,8,row,new URL(urlPrefix+"/"+report.getDescVoice()),"点击下载语音");
				ws.addHyperlink(hy);
			}
			if (StringUtils.isNotBlank(report.getDescVideo())){
				WritableHyperlink hyv = new WritableHyperlink(10,row,10,row,new URL(urlPrefix+"/"+report.getDescVideo()),"点击下载视频");
				ws.addHyperlink(hyv);
			}
			if (imgs != null){
				for(int i=0;i<imgs.length;i++){
					WritableHyperlink hyperlink = new WritableHyperlink(9,row,9,row,new URL(CommonUtils.getImgStr(imgs[i],urlPrefix)),"点击查看图片");
					ws.addHyperlink(hyperlink);
					if (i <imgs.length-1){
						row++;
					}
				}
			}
		}
		wwb.write();
		wwb.close();

	}

    
}
