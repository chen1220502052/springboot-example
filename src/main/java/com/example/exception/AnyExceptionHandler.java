package com.example.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSONObject;


@ControllerAdvice
public class AnyExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(AnyExceptionHandler.class);
    
    @ExceptionHandler(Exception.class)
    public String handlerGlobalException(Exception exception, HttpServletResponse response){
        log.error(exception.getMessage(), exception);
        JSONObject result = new JSONObject();
        result.put("code", 500);
        result.put("message", "服务器出错");
        
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result.toJSONString());
            writer.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            if(writer != null){
                writer.close();
            }
        }
        return null;
    }
}
