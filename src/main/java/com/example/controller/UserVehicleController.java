package com.example.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.example.annotation.TokenVerifyAnnotation;
import com.example.model.User;
import com.example.sevice.UserServiceImpl;


@Controller
public class UserVehicleController {
    
    private static final Logger log = LoggerFactory.getLogger(UserVehicleController.class);
    
    @Autowired
    private UserServiceImpl userService;
    
    @TokenVerifyAnnotation
    @RequestMapping("/say")
    public String sayHello(ModelMap model, HttpServletRequest request, HttpServletResponse response){
        String param = request.getParameter("param");
        log.info("/say?param=" + param);
        
        JSONObject result = new JSONObject();
        result.put("code", 200);
        result.put("parma", param);
        
        User user = new User();
        user.setName("chenqiankun");
        user.setPassword("666666");
        if(userService.add(user)){
            result.put("user_id", user.getId());
        }
        
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
