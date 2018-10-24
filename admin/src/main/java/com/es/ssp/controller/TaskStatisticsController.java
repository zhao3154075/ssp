
package com.es.ssp.controller;

import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Valid;

import common.util.DateUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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

import java.io.*;
import java.net.*;
import java.util.*;

import java.math.*;

import common.base.*;

import static common.util.DateFormats.*;
import static common.util.GlobalMessages.*;
import static common.util.SpringMVCUtils.toModelMap;
import static common.util.MybatisPageQueryUtils.pageQuery;

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;
import static cn.org.rapid_framework.beanutils.BeanUtils.copyProperties;
import static cn.org.rapid_framework.util.holder.BeanValidatorHolder.validateWithException;
import cn.org.rapid_framework.util.DateConvertUtils;

import cn.org.rapid_framework.page.PageRequest;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.*;

import com.es.ssp.model.*;
import com.es.ssp.dao.*;
import com.es.ssp.service.*;
import com.es.ssp.query.*;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/taskstatistics")
public class TaskStatisticsController extends BaseController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "createTime desc";

	@Autowired
	private TaskStatisticsManager taskStatisticsManager;

    @ModelAttribute
    public void init(ModelMap model,Integer recordId) {
		if(!ObjectUtils.isNullOrEmptyString(recordId)){
		    model.addAttribute("taskStatistics",taskStatisticsManager.getById(recordId));
		    }
	}
	
	/** 列表 */
	@RequestMapping
	public String index(ModelMap model,TaskStatisticsQuery query) {
		int year= DateUtils.getNowYear();
		if(query.getYear()==null||query.getYear()==0){
			query.setYear(year);
		}
		ArrayList years=new ArrayList();
		for(;year>=2018;year--){
			years.add(year);
		}
		model.addAttribute("years",years);
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		Page page = this.taskStatisticsManager.findPage(query);
		model.addAllAttributes(toModelMap(page, query));
		return "taskstatistics/list";
	}

	@RequestMapping("/export")
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	public void export(TaskStatisticsQuery query,HttpServletResponse response){
		try {
			taskStatisticsManager.export(query, getOutputStream(response, "志愿者个人数据统计"+System.currentTimeMillis()/1000+".xls"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}


}

