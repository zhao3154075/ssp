
package com.es.ssp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.es.ssp.annotation.ResumeList;
import common.base.BaseController;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
import static common.util.MybatisPageQueryUtils.pageQuery;

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;
import static cn.org.rapid_framework.beanutils.BeanUtils.copyProperties;

import cn.org.rapid_framework.util.*;

import com.es.ssp.model.*;
import com.es.ssp.query.*;
import com.es.ssp.service.*;


/**
 * 标准的rest方法列表
 * <pre>
 * /prizerecord                => index()  
 * /prizerecord/new            => _new()  注意: 不使用/userinfo/add => add()的原因是ad会被一些浏览器当做广告URL拦截
 * /prizerecord/{id}           => show()  
 * /prizerecord/{id}/edit      => edit()  
 * /prizerecord        POST    => create()  
 * /prizerecord/{id}   PUT     => update()  
 * /prizerecord/{id}   DELETE  => delete()  
 * /prizerecord        DELETE  => batchDelete()  
 * </pre>
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/prizerecord")
public class PrizeRecordController extends BaseController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "createTime desc";

	@Autowired
	private PrizeRecordManager prizeRecordManager;
	@Autowired
	private RedpackManager redpackManager;
	
	private final String LIST_ACTION = "redirect:/prizerecord";
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}

    @ModelAttribute
    public void init(ModelMap model,Long recordId) {
		if(!ObjectUtils.isNullOrEmptyString(recordId)){
		    model.addAttribute("prizeRecord",prizeRecordManager.getById(recordId));
		    }
	}
	
	/** 列表 */
	@RequestMapping
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String index(ModelMap model,PrizeRecordQuery query,HttpServletRequest request) {
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		Page page = prizeRecordManager.findPage(query);
		model.addAllAttributes(toModelMap(page, query));
		return "prizerecord/list";
	}


	/** 补发 */
	@ResumeList
	@RequestMapping("/{recordId}/resent")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String resent(@PathVariable Long recordId){
		PrizeRecord prizeRecord=prizeRecordManager.getById(recordId);
		if(prizeRecord!=null&&prizeRecord.getStatus()==-1){
			prizeRecord.setStatus(0);
			prizeRecord.setErrorInfo("");
			prizeRecordManager.update(prizeRecord);
			redpackManager.resentRedpack(recordId);
		}
		return "";
	}

	@RequestMapping("/export")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public void export(PrizeRecordQuery query,HttpServletResponse response){
		try {
			prizeRecordManager.export(query, getOutputStream(response, "初始发放记录"+System.currentTimeMillis()/1000+".xls"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

