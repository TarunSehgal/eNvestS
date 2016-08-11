package com.eNvestDetails.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

public class CORSFilter extends OncePerRequestFilter {
    private static final Logger LOG = Logger.getLogger(CORSFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        LOG.info("about to set pre flight filter for request: "+ request.getPathInfo());
        
        Enumeration<String> headers = request.getHeaderNames();

        while(headers.hasMoreElements()){
        	LOG.info("headers in request:" + headers.nextElement());
        }
       
        
        if (/*request.getHeader("Access-Control-Request-Method") != null &&*/ "OPTIONS".equals(request.getMethod())) {
            LOG.info("setting pre flight filter for request: "+ request.getPathInfo());
            // CORS "pre-flight" request
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
            // response.addHeader("Access-Control-Allow-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Origin,Content-Type,X-Auth-Token");
            response.addHeader("Access-Control-Max-Age", "3600");
            LOG.info("flushing buffer for request for option method:"+request.getPathInfo());
            response.flushBuffer();
        }else{
        	filterChain.doFilter(request, response);
        }        
    }
}