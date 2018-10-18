
package com.es.ssp.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import common.base.BaseController;
import common.util.DateUtils;
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

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;
import cn.org.rapid_framework.util.*;

import com.es.ssp.model.*;
import com.es.ssp.service.*;
import com.es.ssp.query.*;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/reportstatistics")
public class ReportStatisticsController extends BaseController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "updateTime desc";

	@Autowired
	private ReportStatisticsManager reportStatisticsManager;
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

    @ModelAttribute
    public void init(ModelMap model,Integer recordId) {
		if(!ObjectUtils.isNullOrEmptyString(recordId)){
		    model.addAttribute("reportStatistics",reportStatisticsManager.getById(recordId));
		    }
	}
	
	/** 列表 */
	@RequestMapping
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String index(ModelMap model,ReportStatisticsQuery query,HttpServletRequest request) {
		int year=DateUtils.getNowYear();
		if(query.getYear()==null||query.getYear()==0){
			query.setYear(year);
		}
		ArrayList years=new ArrayList();
		for(;year>=2018;year--){
			years.add(year);
		}
		model.addAttribute("years",years);
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		Page page = this.reportStatisticsManager.findPage(query);
		model.addAllAttributes(toModelMap(page, query));
		return "reportstatistics/list";
	}

	@RequestMapping("/export")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public void export(ReportStatisticsQuery query,HttpServletResponse response){
		try {
			reportStatisticsManager.export(query, getOutputStream(response, "用户分析" + System.currentTimeMillis() / 1000 + ".xls"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}

