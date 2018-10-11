package com.es.ssp.service;

import cn.org.rapid_framework.page.Page;
import com.es.ssp.dao.FansDao;
import com.es.ssp.model.Fans;
import com.es.ssp.query.FansQuery;
import com.es.ssp.wechatapi.WechatApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import weixin.popular.bean.user.User;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class FansManager {

	@Autowired
	private FansDao fansDao;
	@Autowired
	private WechatApi wechatApi;

	/** 
	 * 创建Fans
	 **/
	public Fans save(Fans fans) {
	    this.fansDao.save(fans);
	    return fans;
	}
	
	/** 
	 * 更新Fans
	 **/	
    public Fans update(Fans fans) {
        this.fansDao.update(fans);
        return fans;
    }	
    
	/** 
	 * 删除Fans
	 **/
    public void removeById(Integer id) {
        this.fansDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到Fans
	 **/    
    public Fans getById(Integer id) {
        return this.fansDao.getById(id);
    }
    
	/** 
	 * 分页查询: Fans
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(FansQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return fansDao.findPage(query);
	}

	/**
	 * 通过openId查找粉丝
	 * @param openId
	 * @return
	 */
	public Fans findByOpenId(String openId){
		return fansDao.getByPropertys("openId",openId);
	}

	public Fans getUnknownFans(String openId){
		if(openId==null){
			return null;
		}
		Fans fans=findByOpenId(openId);
		if(fans==null){
			fans=new Fans();
			fans.setRealName("");
			fans.setMobile("");
			fans.setCreateTime(System.currentTimeMillis()/1000);
			fans.setOpenId(openId);
			updateFansInfo(fans);
			save(fans);
		}else{
			updateFansInfo(fans);
			update(fans);
		}
		return fans;
	}

	public void updateFansInfo(Fans fans){
		if(fans!=null){
			User user=wechatApi.userinfo(fans.getOpenId());
			if(user.getErrcode()==null){
				fans.setNickName(user.getNickname());
				fans.setHeadimgurl(user.getHeadimgurl());
				fans.setSubscribe(user.getSubscribe());
			}
		}
	}
    
}
