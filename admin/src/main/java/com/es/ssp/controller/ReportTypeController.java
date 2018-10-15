
package com.es.ssp.controller;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.ObjectUtils;
import com.es.ssp.annotation.ResumeList;
import com.es.ssp.annotation.enums.ResumeListMethod;
import com.es.ssp.model.ReportType;
import com.es.ssp.query.ReportTypeQuery;
import com.es.ssp.service.ReportTypeManager;
import common.base.BaseController;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;
import static common.util.SpringMVCUtils.toModelMap;


/**
 * 标准的rest方法列表
 * <pre>
 * /reporttype                => index()  
 * /reporttype/new            => _new()  注意: 不使用/userinfo/add => add()的原因是ad会被一些浏览器当做广告URL拦截
 * /reporttype/{id}           => show()  
 * /reporttype/{id}/edit      => edit()  
 * /reporttype        POST    => create()  
 * /reporttype/{id}   PUT     => update()  
 * /reporttype/{id}   DELETE  => delete()  
 * /reporttype        DELETE  => batchDelete()  
 * </pre>
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/reporttype")
public class ReportTypeController extends BaseController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	@Autowired
	private ReportTypeManager reportTypeManager;
	
	private final String LIST_ACTION = "redirect:/reporttype";
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}

    @ModelAttribute
    public void init(ModelMap model,Integer typeId) {
		if(!ObjectUtils.isNullOrEmptyString(typeId)){
		    model.addAttribute("reportType",reportTypeManager.getById(typeId));
		    }
	}
	
	/** 列表 */
	@RequestMapping
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	public String index(ModelMap model,ReportTypeQuery query,HttpServletRequest request) {
		model.addAttribute("parents",reportTypeManager.getParents());
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		Page page = reportTypeManager.findPage(query);
		model.addAllAttributes(toModelMap(page, query));
		return "reporttype/list";
	}
	
	/** 显示 */
	@RequestMapping(value="/{id}")
	public String show(ModelMap model,@PathVariable Integer id) throws Exception {
		ReportType reportType = (ReportType)reportTypeManager.getById(id);
		model.addAttribute("reportType",reportType);
		return "reporttype/show";
	}

	/** 进入新增 */
	@RequestMapping(value="/new")
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	public String _new(ModelMap model,ReportType reportType) throws Exception {
		model.addAttribute("parents",reportTypeManager.getParents());
		model.addAttribute("reportType",reportType);
		return "reporttype/new";
	}
	
	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method=RequestMethod.POST)
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	public String create(ModelMap model,ReportType reportType,BindingResult errors) throws Exception {
		try {
			reportType.setAmount(reportType.getAmount()*100);
			reportType.setCreateTime(System.currentTimeMillis()/1000);
			reportTypeManager.save(reportType);
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "reporttype/new";
		}catch(ValidationException e) {
			return  "reporttype/new";
		}
		return LIST_ACTION;
	}
	
	/** 编辑 */
	@RequestMapping(value="/{id}/edit")
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	@ResumeList(action = ResumeListMethod.BEFORE)
	public String edit(ModelMap model,@PathVariable Integer id) throws Exception {
		model.addAttribute("parents",reportTypeManager.getParents());
		ReportType reportType = (ReportType)reportTypeManager.getById(id);
		model.addAttribute("reportType",reportType);
		return "reporttype/edit";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	@ResumeList(action = ResumeListMethod.AFTER)
	public String update(ModelMap model,@PathVariable Integer id,ReportType reportType,BindingResult errors) throws Exception {
		try {
			reportType.setAmount(reportType.getAmount()*100);
			reportTypeManager.update(reportType);
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "reporttype/edit";
		}catch(ValidationException e) {
			return  "reporttype/edit";
		}
		return LIST_ACTION;
	}
	
	@RequestMapping(value = "/list")
	@RequiresRoles(value = {"admin","firstLevelAdmin","secondLevelAdmin"},logical = Logical.OR)
	public String list(ModelMap model,ReportTypeQuery query){
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
        query.setChildren("true");
		Page page = reportTypeManager.findPage(query);
		model.addAllAttributes(toModelMap(page, query));
		return "reporttype/global_setting_app_list";
	}

	/*@RequestMapping(value = "/{id}/editApp")
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	@ResumeList(action = ResumeListMethod.BEFORE)
	public String editApp(ModelMap model,@PathVariable Integer id) throws Exception {
		ReportType reportType = (ReportType)reportTypeManager.getById(id);
		model.addAttribute("reportType",reportType);
		return "reporttype/global_setting_app";
	}

	@RequestMapping(value="/update")
	@RequiresRoles(value = {"admin"},logical = Logical.OR)
	@ResumeList(action = ResumeListMethod.AFTER)
	public String updateApp(ModelMap model,Integer id,Integer amount) throws Exception {
		try {
			if (id != null){
				ReportType reportType = reportTypeManager.getById(id);
				reportType.setAmount(amount*100);
				reportTypeManager.update(reportType);
			}
		}catch(Exception e) {
			return  "reporttype/global_setting_app";
		}
		return "redirect:/reporttype/list";
	}
*/
}

