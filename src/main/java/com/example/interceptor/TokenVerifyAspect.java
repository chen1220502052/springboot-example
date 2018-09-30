package com.example.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.example.annotation.TokenVerifyAnnotation;

@Aspect
@Component
@Order(1)
public class TokenVerifyAspect {
    
    Logger log = LoggerFactory.getLogger(TokenVerifyAspect.class);
    
    public TokenVerifyAspect() {
        log.info("create a bean token verify aspect");
    }

    
    @Around("within(@org.springframework.stereotype.Controller *) && @annotation(is)")
    public Object verify(ProceedingJoinPoint pjp, TokenVerifyAnnotation is)throws Throwable{
        log.info("start process verify token");
        
        Object[] args = pjp.getArgs();
        HttpServletRequest request = null;
        ModelMap model = null;
        
        if(args != null && args.length != 0){
            for(Object arg:args){
                if(arg != null){
                    if(arg instanceof HttpServletRequest){
                        request = (HttpServletRequest)arg;
                        continue;
                    }
                    if(arg instanceof ModelMap){
                        model = (ModelMap)arg;
                        continue;
                    }
                }
            }
        }
        if(request == null || model == null){
            throw new Exception("request and model params should be put into the controller.");
        }
        String token = request.getParameter("token");
        
        log.info("token=" + token);
        
        return pjp.proceed();
    }
}
