package com.quesito.webstore.interceptor;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessingTimeInterceptor implements HandlerInterceptor {

    private static  final Logger logger=Logger.getLogger(ProcessingTimeInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime=System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String queryString=request.getQueryString()==null?"":request.getQueryString();
        String path= request.getRequestURL().toString();
        long starTime=(Long) request.getAttribute("startTime");
        long endTime=System.currentTimeMillis();
        logger.info(String.format("%s millisecond taken to procces thr request %s.",(endTime-starTime),path));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
