
package com.es.ssp.controller;

import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Valid;

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
@RequestMapping("/reporttask")
public class ReportTaskController extends BaseController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "createTime desc";

	@Autowired
	private ReportTaskManager reportTaskManager;
	
	private final String LIST_ACTION = "redirect:/reporttask";
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}

    @ModelAttribute
    public void init(ModelMap model,Integer taskId) {
		if(!ObjectUtils.isNullOrEmptyString(taskId)){
		    model.addAttribute("reportTask",reportTaskManager.getById(taskId));
		    }
	}
	
	/** 列表 */
	@RequestMapping
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	public String index(ModelMap model,ReportTaskQuery query,HttpServletRequest request) {
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		Page page = this.reportTaskManager.findPage(query);
		model.addAllAttributes(toModelMap(page, query));
		return "reporttask/list";
	}
	
	/** 显示 */
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	@RequestMapping(value="/{id}")
	public String show(ModelMap model,@PathVariable Integer id) {
		ReportTask reportTask = (ReportTask)reportTaskManager.getById(id);
		model.addAttribute("reportTask",reportTask);
		return "reporttask/show";
	}

	/** 进入新增 */
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	@RequestMapping(value="/new")
	public String _new(ModelMap model,ReportTask reportTask) {
		model.addAttribute("reportTask",reportTask);
		return "reporttask/new";
	}
	
	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	@RequestMapping(method=RequestMethod.POST)
	public String create(ModelMap model,ReportTask reportTask){
		reportTask.setCreateTime(System.currentTimeMillis()/1000);
		reportTaskManager.save(reportTask);
		reportTaskManager.statistics(0,reportTask.getTaskId().longValue());
		return LIST_ACTION;
	}
	
	/** 编辑 */
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	@RequestMapping(value="/{id}/edit")
	public String edit(ModelMap model,@PathVariable Integer id) {
		ReportTask reportTask = reportTaskManager.getById(id);
		model.addAttribute("reportTask",reportTask);
		return "reporttask/edit";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	public String update(ReportTask reportTask){
		reportTaskManager.update(reportTask);
		reportTaskManager.statistics(0,reportTask.getTaskId().longValue(),getParam("lastTown"));
		return LIST_ACTION;
	}
	
	/** 删除 */
	@RequestMapping(value="/{id}/delete",method=RequestMethod.GET)
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	public String delete(ModelMap model,@PathVariable Integer id) {
		reportTaskManager.statistics(1,id.longValue());
		return LIST_ACTION+"?d="+id.longValue();
	}

}

