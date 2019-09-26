package com.dc.base.filter;

import com.dc.base.contants.BaseContants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-23 9:08
 */
@WebFilter(urlPatterns = "*.html")
public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();
        if (uri != null && !"/login.html".equals(uri)) {//如果不是登录页面，则进行拦截
//判断是否登录
            Object user = ((HttpServletRequest) request).getSession().getAttribute(BaseContants.LOGIN_USER);
            if (user == null) {//说明未登录
                ((HttpServletResponse) response).sendRedirect("/login.html");
                return;
            }
        }
        System.out.println("**************" + uri);

        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
