package com.xk.bbs.interceptor;


import com.xk.bbs.bean.User;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    Logger log = Logger.getLogger(LoginInterceptor.class);
    String TAG = LoginInterceptor.class.getSimpleName();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String url = request.getRequestURI();
        log.error(TAG+"  preHandle() method url:  "+url);
        // 注册
        if (url.equals("/") || url.equals("/favicon.ico") || url.equals("/login")) {
            return true;
        } else {
            User user = (User) request.getSession().getAttribute("curr_user");
            if (user != null) {
                return true;
            } else {
                response.sendRedirect("/");
                return false;
            }
        }
    }



    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
