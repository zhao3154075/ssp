package common.base;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/5/26.
 */
public class BaseController {
    public HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    public String getParam(String name){
        return getRequest().getParameter(name);
    }
    public Long getLongParam(String name){
        if(name==null)return 0l;
        return Long.parseLong(getParam(name));
    }
    public String[] getParams(String name){
        return getRequest().getParameterValues(name);
    }
    public <T> T  getAttribute(String name){
        Object o=getRequest().getAttribute(name);
        if(o!=null){
            return (T)o;
        }
        return null;
    }
    public  void setAttribute(String name,Object value){
        getRequest().setAttribute(name, value);
    }
    public void setSessionAttr(String name,Object value){
        getRequest().getSession().setAttribute(name,value);
    }
    public void removeSessionAttr(String name){
        getRequest().getSession().removeAttribute(name);
    }
    public Object getSessionAttr(String name){
        return getRequest().getSession().getAttribute(name);
    }
    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
    public String getBasePath(){
        HttpServletRequest request=getRequest();
        return request.getScheme() + "://"
                + request.getServerName()+request.getContextPath()+"/";
    }
    public String getUri(){
        HttpServletRequest request=getRequest();
        String basePath=getBasePath();
        String uri=request.getRequestURI().replace(request.getContextPath()+"/","");
        if(request.getQueryString()!=null){
            uri+="?"+request.getQueryString();
        }
        return basePath+uri;
    }

    public JSONObject jsonMsg(Integer code,String msg){
        JSONObject jo=new JSONObject();
        jo.put("code",code);
        jo.put("msg",msg);
        return jo;
    }

    public String alert(String msg){
        setAttribute("outResult",msg);
        return "/alert";
    }

    public void setDefaultSortColumns(PageRequest pageQuery, String defaultSortColumns){
        if(StringUtils.isEmpty(pageQuery.getSortColumns())&&StringUtils.isNotEmpty(defaultSortColumns)){
            pageQuery.setSortColumns(defaultSortColumns);
        }
    }

    public JSONObject pageJson(Page page, JSONArray jsonArray){
        return pageJson(page,jsonArray,null);
    }

    public JSONObject pageJson(Page page, JSONArray jsonArray,String exNames,Object...exValues){
        JSONObject jo=new JSONObject();
        jo.put("code","0");
        jo.put("msg","success");
        jo.put("data",jsonArray);
        if(exNames!=null){
            String []names=exNames.split(",");
            for(int i=0;i<names.length;i++){
                jo.put(names[i],exValues[i]);
            }
        }
        jo.put("page",page.getThisPageNumber());
        jo.put("totalPage",page.getLastPageNumber());
        jo.put("previousPage",page.getPreviousPageNumber());
        jo.put("nextPage",page.getNextPageNumber());
        jo.put("linkPageNumbers",page.getLinkPageNumbers());
        jo.put("totalCount",page.getTotalCount());
        return jo;
    }

    public OutputStream getOutputStream(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType("application/xls;charset=utf-8");
        response.setHeader("Content-disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        return response.getOutputStream();
    }
}
