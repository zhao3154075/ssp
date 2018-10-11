package com.es.ssp.interceptor;

import com.es.ssp.annotation.ResumeList;
import com.es.ssp.annotation.enums.ResumeListMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于拦截ResumeList标签
 */
public class ResumeListInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod))return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.getBean().getClass().isAnnotationPresent(ResumeList.class)||handlerMethod.hasMethodAnnotation(ResumeList.class)) {
            ResumeList resumeList=handlerMethod.getMethodAnnotation(ResumeList.class);
            if(resumeList!=null&&resumeList.action()!=null){
                if(resumeList.action().equals(ResumeListMethod.BEFORE)||resumeList.action().equals(ResumeListMethod.NULL)){
                    String referer = request.getHeader("referer");
                    request.getSession().setAttribute("referer",referer);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if(!(handler instanceof HandlerMethod))return;
        if (modelAndView == null) {
            return;
        }
        String viewName = modelAndView.getViewName();
        if (viewName != null ) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if(handlerMethod.hasMethodAnnotation(ResumeList.class)){
                ResumeList resumeList=handlerMethod.getMethodAnnotation(ResumeList.class);
                if(resumeList!=null&&resumeList.action()!=null){
                    if(resumeList.action().equals(ResumeListMethod.AFTER)||resumeList.action().equals(ResumeListMethod.NULL)){
                        viewName = (String) request.getSession().getAttribute("referer");
                        viewName = "redirect:" + viewName;
                    }
                    modelAndView.setViewName(viewName);
                }
            }
        }
    }
}
