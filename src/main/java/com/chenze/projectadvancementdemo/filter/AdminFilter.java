package com.chenze.projectadvancementdemo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chenze.projectadvancementdemo.common.Constant;
import com.chenze.projectadvancementdemo.exception.MallException;
import com.chenze.projectadvancementdemo.exception.MallExceptionEnum;
import com.chenze.projectadvancementdemo.model.pojo.User;
import com.chenze.projectadvancementdemo.service.UserService;
import com.chenze.projectadvancementdemo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
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
        String token = request.getHeader(Constant.JWT_TOKEN);
        Algorithm algorithm = Algorithm.HMAC256(Constant.JWT_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            currentAdmin.setId(jwt.getClaim(Constant.USER_ID).asInt());
            currentAdmin.setUsername(jwt.getClaim(Constant.USER_NAME).asString());
            currentAdmin.setRole(jwt.getClaim(Constant.USER_ROLE).asInt());
        }catch (TokenExpiredException e){
            throw new IOException(new MallException(MallExceptionEnum.TOKEN_EXPIRE));
        }catch (JWTDecodeException e){
            throw new IOException(new MallException(MallExceptionEnum.TOKEN_WRONG));
        }
        if (currentAdmin==null||UserServiceImpl.loginOut){
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
