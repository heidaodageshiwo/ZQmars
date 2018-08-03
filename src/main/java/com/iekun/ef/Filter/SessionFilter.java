package com.iekun.ef.Filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by sword.yu on 2016/12/24.
 */

public class SessionFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String strURL =  httpServletRequest.getRequestURI();

        //只过滤了ajax请求时session超时
        if (httpServletRequest.getHeader("x-requested-with") != null
                && httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            //// TODO: 2016/12/24
            if( (!strURL.equals("/user/loginPost"))
                 &&   (!strURL.equals("/user/findPwdPost"))
                    &&   (!strURL.equals("/uploadFile"))
                    &&   (!strURL.equals("/uploadLicFile"))
                 &&   (!strURL.equals("/system/updateLicense"))
                ) {
                if (!SecurityUtils.getSubject().isAuthenticated()) {
//                    Session session = SecurityUtils.getSubject().getSession();
                    logger.debug("Filter：session status timeout！");
                    httpServletResponse.setHeader("sessionstatus", "timeout");
                    return;
                }

            }

        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
