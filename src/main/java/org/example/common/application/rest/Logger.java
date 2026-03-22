package org.example.common.application.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;



@Slf4j
@Component
public class Logger extends OncePerRequestFilter {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        var requestURL = requestWrapper.getRequestURL().toString();
        var requestMethod = requestWrapper.getMethod();
        var requestAccept= requestWrapper.getHeader("Accept");
        var requestContentType = requestWrapper.getContentType();
        var requestApikey = requestWrapper.getHeader("apikey");
        var requestUserid = requestWrapper.getHeader("userid");
        var requestQuery = requestWrapper.getQueryString();

        if(requestURL.contains("healthcheck") || requestURL.contains("healtcheck")){
            return;
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("requestURL",requestURL);
            obj.put("requestMethod",requestMethod);
            obj.put("requestAccept",requestAccept);
            obj.put("requestContentType",requestContentType);
            obj.put("requestApikey",requestApikey);
            obj.put("requestUserid",requestUserid);
            obj.put("requestQuery",requestQuery);

            logger.log(Level.OFF,"Request: {}", obj.toString());
        } catch (Exception e) {
            logger.error("Failed to save request", e);
        }

        filterChain.doFilter(requestWrapper, responseWrapper);

        var responseCode= String.valueOf(responseWrapper.getStatus());

        try {
            var responseBody = new String(responseWrapper.getContentAsByteArray());
            var requestBody = new String(requestWrapper.getContentAsByteArray());

            obj.put("requestBody",requestBody.isBlank() ? null :
                    requestBody.startsWith("{") ? new JSONObject(requestBody) : new JSONArray(requestBody));
            obj.put("responseBody",responseBody.isBlank() ? null :
                    responseBody.startsWith("{") ? new JSONObject(responseBody) : new JSONArray(responseBody));
            obj.put("responseCode",responseCode);

            logger.log(Level.OFF,"Response: {}", obj.toString());
        } catch (Exception e) {
            logger.error("Failed to save response");
        }
        responseWrapper.copyBodyToResponse();

    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch(){
        return true;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch(){
        return true;
    }
}
