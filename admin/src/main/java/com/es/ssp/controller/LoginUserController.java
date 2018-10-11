
package com.es.ssp.controller;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.ObjectUtils;
import com.alibaba.fastjson.JSONObject;
import com.es.ssp.annotation.ResumeList;
import com.es.ssp.annotation.enums.ResumeListMethod;
import com.es.ssp.model.LoginUser;
import com.es.ssp.model.UserRole;
import com.es.ssp.query.LoginUserQuery;
import com.es.ssp.service.LoginUserManager;
import com.es.ssp.service.UserRoleManager;
import common.base.BaseController;
import common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
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
import java.util.List;

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;
import static common.util.SpringMVCUtils.toModelMap;


/**
 * 标准的rest方法列表
 * <pre>
 * /loginuser                => index()  
 * /loginuser/new            => _new()  注意: 不使用/userinfo/add => add()的原因是ad会被一些浏览器当做广告URL拦截
 * /loginuser/{id}           => show()  
 * /loginuser/{id}/edit      => edit()  
 * /loginuser        POST    => create()  
 * /loginuser/{id}   PUT     => update()  
 * /loginuser/{id}   DELETE  => delete()  
 * /loginuser        DELETE  => batchDelete()  
 * </pre>
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/loginuser")
public class LoginUserController extends BaseController{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginUserController.class);
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "createTime desc";

	@Autowired
	private LoginUserManager loginUserManager;
	@Autowired
	private UserRoleManager userRoleManager;
	private final String LIST_ACTION = "redirect:/loginuser";
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}

    @ModelAttribute
    public void init(ModelMap model,Integer userId) {
		if(!ObjectUtils.isNullOrEmptyString(userId)){
		    model.addAttribute("loginUser",loginUserManager.getById(userId));
		    }
	}
	
	/** 列表 */
	@RequestMapping
	@RequiresRoles(value = {"admin"})
	public String index(ModelMap model,LoginUserQuery query,HttpServletRequest request) {
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		Page page = loginUserManager.findPage(query);
		model.addAllAttributes(toModelMap(page, query));
		return "loginuser/list";
	}
	
	/** 显示 */
	@RequestMapping(value="/{id}")
	public String show(ModelMap model,@PathVariable Integer id) throws Exception {
		LoginUser loginUser = (LoginUser)loginUserManager.getById(id);
		model.addAttribute("loginUser",loginUser);
		return "loginuser/show";
	}

	/** 进入新增 */
	@RequestMapping(value="/new")
	@RequiresRoles(value = {"admin"})
	public String _new(ModelMap model,LoginUser loginUser) throws Exception {
		model.addAttribute("loginUser",loginUser);
		return "loginuser/new";
	}
	
	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method=RequestMethod.POST)
	@RequiresRoles(value = {"admin"})
	public String create(ModelMap model,LoginUser loginUser,UserRole userRole,BindingResult errors) throws Exception {
		try {
			loginUser.setCreateTime(System.currentTimeMillis()/1000);
			loginUser.setStatus(1);
			loginUser.setPsw(CommonUtils.getMD5(loginUser.getPsw()));
			LoginUser loginUsersList = loginUserManager.getByUserName(loginUser.getUserName());
			if (loginUsersList==null){
			loginUserManager.save(loginUser);
				userRole.setRoleId(loginUser.getRoleType());
				userRole.setUserId(loginUser.getUserId());
				userRoleManager.save(userRole);
			}else{
				//Flash.current().error("用户名已存在！");
				return  "loginuser/new";
			}

		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "loginuser/new";
		}catch(ValidationException e) {
			//Flash.current().error(e.getMessage());
			return  "loginuser/new";
		}
		//Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/** 编辑 */
	@ResumeList(action = ResumeListMethod.BEFORE)
	@RequiresRoles(value = {"admin"})
	@RequestMapping(value="/{id}/edit")
	public String edit(ModelMap model,@PathVariable Integer id) throws Exception {
		LoginUser loginUser = (LoginUser)loginUserManager.getById(id);
		model.addAttribute("loginUser",loginUser);
		return "loginuser/edit";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	@RequiresRoles(value = {"admin"})
	@ResumeList(action = ResumeListMethod.AFTER)
	public String update(ModelMap model,@PathVariable Integer id,LoginUser loginUser,UserRole userRole,BindingResult errors) throws Exception {
		try {
            loginUser.setPsw(CommonUtils.getMD5(loginUser.getPsw()));
            LoginUser thisLoginUser = loginUserManager.getById(id);
            thisLoginUser.setRoleType(loginUser.getRoleType());
            thisLoginUser.setMobile(loginUser.getMobile());
            thisLoginUser.setRealName(loginUser.getRealName());
            if(thisLoginUser.getUserName().equals(loginUser.getUserName())){
            	thisLoginUser.setUserName(loginUser.getUserName());
                loginUserManager.update(thisLoginUser);
                List<UserRole> userRoleList = userRoleManager.findUserRoles(loginUser.getUserId());
                if(userRoleList.size()>0){
                   UserRole userRole1 = userRoleList.get(0);
                    userRole1.setRoleId(loginUser.getRoleType());
                    userRoleManager.update(userRole1);
                }
                return LIST_ACTION;
            }else{
                LoginUser loginUsers = loginUserManager.getByUserName(loginUser.getUserName());
                if (loginUsers==null){
					thisLoginUser.setUserName(loginUser.getUserName());
                    loginUserManager.update(thisLoginUser);
                    List<UserRole> userRoleList = userRoleManager.findUserRoles(loginUser.getUserId());
                    if(userRoleList.size()>0){
                        UserRole userRole1 = userRoleList.get(0);
                        userRole1.setRoleId(loginUser.getRoleType());
                        userRoleManager.update(userRole1);
                    }
                }else{
                    //Flash.current().error("用户名已存在！");
                    return  "loginuser/edit";
                }
            }
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "/loginuser/edit";
		}catch(ValidationException e) {
			return  "/loginuser/edit";
		}
		return LIST_ACTION;
	}

	@RequestMapping(value="/changePsw")
	public String changePsw(){
		return "loginuser/update_psw";
	}

	@RequestMapping(value="/updatePsw")
	public String updatePsw(){
		Subject currentUser = SecurityUtils.getSubject();
		Object o=currentUser.getSession().getAttribute("userId");
		if(o!=null){
			Integer userId=(Integer)o;
			LoginUser loginUser=loginUserManager.getById(userId);
			if(loginUser.getPsw().equals(CommonUtils.getMD5(getParam("oldPsw")))){
				loginUser.setPsw(CommonUtils.getMD5(getParam("psw")));
				loginUserManager.update(loginUser);
				setAttribute("msg","1");
			}else{
				setAttribute("msg","0");
			}
		}
		return "loginuser/update_psw";
	}

	/**取消禁用**/
	@ResumeList
	@RequiresRoles(value = {"admin"})
	@RequestMapping(value="/{id}/enable")
	public String enable(@PathVariable Integer id){
		LoginUser loginUser = loginUserManager.getById(id);
		loginUser.setStatus(1);
		loginUserManager.update(loginUser);
		return LIST_ACTION;
	}

	/**禁用**/
	@ResumeList
	@RequiresRoles(value = {"admin"})
	@RequestMapping(value="/{id}/disable")
	public String disable(@PathVariable Integer id){
		LoginUser loginUser = loginUserManager.getById(id);
		loginUser.setStatus(0);
		loginUserManager.update(loginUser);
		return LIST_ACTION;
	}

	@RequestMapping(value = "/{id}/resetpsw")
	@RequiresRoles(value = {"admin"})
	@ResponseBody
	public String resetPassword(@PathVariable Integer id){
		LoginUser loginUser = loginUserManager.getById(id);
		loginUser.setPsw(CommonUtils.getMD5("admin123"));
		loginUserManager.update(loginUser);
		JSONObject object = new JSONObject();
		object.put("success",true);
		return object.toJSONString();
	}


	@RequestMapping(value = "/login")
	public String gotoLogin(ModelMap model) {
		return "login";
	}


	@RequestMapping("/toLogin")
	public String toLogin(ModelMap map, String userName, String psd, org.apache.catalina.servlet4preview.http.HttpServletRequest request, String kaptcha){
		if (StringUtils.isNotBlank(userName)){
			LoginUser hasUser = loginUserManager.getByUserName(userName);
			if(hasUser.getStatus()==0){
				map.addAttribute("message","您已被禁用，无法登录！");
				return "login";
			}
			UsernamePasswordToken token = new UsernamePasswordToken(userName, CommonUtils.getMD5(psd));
			Subject currentUser = SecurityUtils.getSubject();
			if (CommonUtils.notEmpty(kaptcha)){
				if (request.getSession().getAttribute("vrifyCode") != null){
					if (!request.getSession().getAttribute("vrifyCode").equals(kaptcha)){
						map.addAttribute("message","验证码不正确");
						token.clear();
						return "login";
					}
				}
			}else {
				map.addAttribute("message","验证码不能为空");
				return "login";
			}
			try {
				//在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
				//每个Realm都能在必要时对提交的AuthenticationTokens作出反应
				//所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
				logger.info("对用户[" + userName + "]进行登录验证..验证开始");
				currentUser.login(token);
				logger.info("对用户[" + userName + "]进行登录验证..验证通过");
			}catch(UnknownAccountException uae){
				logger.info("对用户[" + userName+ "]进行登录验证..验证未通过,未知账户");
				map.addAttribute("message", "未知账户");
				token.clear();
				return "login";
			}catch(IncorrectCredentialsException ice){
				logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
				map.addAttribute("message", "用户名或密码不正确");
				token.clear();
				return "login";
			}catch(LockedAccountException lae){
				logger.info("对用户[" + userName + "]进行登录验证..验证未通过,账户已锁定");
				map.addAttribute("message", "账户已锁定");
				token.clear();
				return "login";
			}catch(ExcessiveAttemptsException eae){
				logger.info("对用户[" + userName+ "]进行登录验证..验证未通过,错误次数过多");
				map.addAttribute("message", "用户名或密码错误次数过多");
				token.clear();
				return "login";
			}catch(AuthenticationException ae){
				//通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
				logger.info("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
				ae.printStackTrace();
				map.addAttribute("message", "用户名或密码不正确");
				token.clear();
				return "login";
			}
			//验证是否登录成功
			if(currentUser.isAuthenticated()){
				logger.info("用户[" + userName + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
				LoginUser user=loginUserManager.getByUserName(userName);

				currentUser.getSession().setAttribute("user",user);
				currentUser.getSession().setAttribute("userId",user.getUserId());
				currentUser.getSession().setAttribute("username",user.getUserName());
				currentUser.getSession().setAttribute("loginFlag","T");
				return "redirect:/";
			}else{
				token.clear();
				return "login";
			}
		}
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(){
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		currentUser.getSession().removeAttribute("user");
		return "login";
	}

	
}

