package com.chenze.projectadvancementdemo.filter;

import com.chenze.projectadvancementdemo.common.Constant;
import com.chenze.projectadvancementdemo.model.pojo.User;
import com.chenze.projectadvancementdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AdminFilter implements Filter {
    public static User currentAdmin=new User();
    @Autowired
    UserService userService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
         HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
         currentAdmin = (User) session.getAttribute(Constant.USER_KEY);
         if (currentAdmin==null){
              PrintWriter out = new HttpServletResponseWrapper
                      ((HttpServletResponse) servletResponse).getWriter();
             out.write("{\n"
                     + "    \"status\": 10010,\n"
                     + "    \"msg\": \"NEED_LOGIN\",\n"
                     + "    \"data\": null\n"
                     + "}");
             out.flush();
             out.close();
             return;
         }

         if (userService.checkAdmin(currentAdmin)){
             filterChain.doFilter(servletRequest,servletResponse);
         }else {
             PrintWriter out = new HttpServletResponseWrapper
                     ((HttpServletResponse) servletResponse).getWriter();
             out.write("{\n"
                     + "    \"status\": 10005,\n"
                     + "    \"msg\": \"NEED_ADMIN_ROLE\",\n"
                     + "    \"data\": null\n"
                     + "}");
             out.flush();
             out.close();
         }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
