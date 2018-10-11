
package com.es.ssp.controller;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.ObjectUtils;
import com.es.ssp.model.OperateRecord;
import com.es.ssp.query.OperateRecordQuery;
import com.es.ssp.service.OperateRecordManager;
import common.base.BaseController;
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
 * /operaterecord                => index()  
 * /operaterecord/new            => _new()  注意: 不使用/userinfo/add => add()的原因是ad会被一些浏览器当做广告URL拦截
 * /operaterecord/{id}           => show()  
 * /operaterecord/{id}/edit      => edit()  
 * /operaterecord        POST    => create()  
 * /operaterecord/{id}   PUT     => update()  
 * /operaterecord/{id}   DELETE  => delete()  
 * /operaterecord        DELETE  => batchDelete()  
 * </pre>
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/operaterecord")
public class OperateRecordController extends BaseController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "createTime desc";

	@Autowired
	private OperateRecordManager operateRecordManager;
	
	private final String LIST_ACTION = "redirect:/operaterecord";
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}

    @ModelAttribute
    public void init(ModelMap model,Long recordId) {
		if(!ObjectUtils.isNullOrEmptyString(recordId)){
		    model.addAttribute("operateRecord",operateRecordManager.getById(recordId));
		    }
	}
	
	/** 列表 */
	@RequestMapping
	public String index(ModelMap model,OperateRecordQuery query,HttpServletRequest request) {
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		Page page = operateRecordManager.findPage(query);
		model.addAllAttributes(toModelMap(page, query));
		return "operaterecord/list";
	}
	
	/** 显示 */
	@RequestMapping(value="/{id}")
	public String show(ModelMap model,@PathVariable Long id) throws Exception {
		OperateRecord operateRecord = (OperateRecord)operateRecordManager.getById(id);
		model.addAttribute("operateRecord",operateRecord);
		return "operaterecord/show";
	}

	/** 进入新增 */
	@RequestMapping(value="/new")
	public String _new(ModelMap model,OperateRecord operateRecord) throws Exception {
		model.addAttribute("operateRecord",operateRecord);
		return "operaterecord/new";
	}
	
	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method=RequestMethod.POST)
	public String create(ModelMap model,OperateRecord operateRecord,BindingResult errors) throws Exception {
		try {
			operateRecordManager.save(operateRecord);
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "operaterecord/new";
		}catch(ValidationException e) {
			return  "operaterecord/new";
		}
		return LIST_ACTION;
	}
	
	/** 编辑 */
	@RequestMapping(value="/{id}/edit")
	public String edit(ModelMap model,@PathVariable Long id) throws Exception {
		OperateRecord operateRecord = (OperateRecord)operateRecordManager.getById(id);
		model.addAttribute("operateRecord",operateRecord);
		return "operaterecord/edit";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(ModelMap model,@PathVariable Long id,OperateRecord operateRecord,BindingResult errors) throws Exception {
		try {
			operateRecordManager.update(operateRecord);
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "operaterecord/edit";
		}catch(ValidationException e) {
			return  "operaterecord/edit";
		}
		return LIST_ACTION;
	}
	
	/** 删除 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(ModelMap model,@PathVariable Long id) {
		operateRecordManager.removeById(id);
		return LIST_ACTION;
	}

	/** 批量删除 */
	@RequestMapping(method=RequestMethod.DELETE)
	public String batchDelete(ModelMap model,@RequestParam("items") Long[] items) {
		for(int i = 0; i < items.length; i++) {
			operateRecordManager.removeById(items[i]);
		}
		return LIST_ACTION;
	}
	
}

