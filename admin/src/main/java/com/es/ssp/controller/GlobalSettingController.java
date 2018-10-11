
package com.es.ssp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import common.base.BaseController;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;

import static common.util.GlobalMessages.*;
import static common.util.SpringMVCUtils.toModelMap;

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;

import cn.org.rapid_framework.util.*;

import com.es.ssp.model.*;
import com.es.ssp.service.*;
import com.es.ssp.query.*;


/**
 * 标准的rest方法列表
 * <pre>
 * /globalsetting                => index()  
 * /globalsetting/new            => _new()  注意: 不使用/userinfo/add => add()的原因是ad会被一些浏览器当做广告URL拦截
 * /globalsetting/{id}           => show()  
 * /globalsetting/{id}/edit      => edit()  
 * /globalsetting        POST    => create()  
 * /globalsetting/{id}   PUT     => update()  
 * /globalsetting/{id}   DELETE  => delete()  
 * /globalsetting        DELETE  => batchDelete()  
 * </pre>
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/globalsetting")
public class GlobalSettingController extends BaseController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 

	@Autowired
	private GlobalSettingManager globalSettingManager;
	
	private final String LIST_ACTION = "redirect:/globalsetting";


    @ModelAttribute
    public void init(ModelMap model,Integer settingId) {
		if(!ObjectUtils.isNullOrEmptyString(settingId)){
		    model.addAttribute("globalSetting",globalSettingManager.getById(settingId));
		    }
	}

	
	/** 编辑 */
	@RequestMapping(value="/edit")
	public String edit(ModelMap model) throws Exception {
		model.addAttribute("globalSetting",globalSettingManager.getOne());
		return "global_setting";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method=RequestMethod.PUT)
	public String update(GlobalSetting globalSetting) {
		GlobalSetting globalSetting1=globalSettingManager.getOne();
		if(globalSetting1==null){
			globalSetting1=new GlobalSetting();
		}
		globalSetting1.setFirstAmount(globalSetting.getFirstAmount()*100);
		globalSetting1.setReceiveType(globalSetting.getReceiveType());
		globalSetting1.setDayLimit(globalSetting.getDayLimit());
		globalSetting1.setUpdateTime(System.currentTimeMillis()/1000);
		globalSettingManager.update(globalSetting1);
		return "redirect:/globalsetting/edit";
	}

	
}

