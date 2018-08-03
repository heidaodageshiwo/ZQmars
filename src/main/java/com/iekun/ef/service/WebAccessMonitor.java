package com.iekun.ef.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.SourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by feilong.cai on 2016/11/12.
 */

@Aspect
@Component
public class WebAccessMonitor {

    public static Logger logger = LoggerFactory.getLogger( WebAccessMonitor.class);

    @Autowired
    LoggerService  loggerServ;
    
    @Autowired
    UserService userServ;
    
    @Pointcut("execution(public * com.iekun.ef.controller..*.*(..)) ")
    public void webAccessLog(){}

    
    @Before("webAccessLog()")
    public void logAccessBefore(JoinPoint joinPoint){
        System.out.println("Before: " + joinPoint);
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        System.out.println("className: " + className);
        System.out.println("methodName: " + methodName);
      
        Object[] args = joinPoint.getArgs();
        for (int i=0; i<args.length; i++) {
            System.out.println("args " + i + args[i]);
        }
        
        if(methodName.equalsIgnoreCase("loginPost"))
        {
           	Long UserId = userServ.getUserIdByLoginUserName(args[1].toString());
        	loggerServ.insertLog((className+'.'+methodName), null, UserId);
        }
        else  
        {
        	loggerServ.insertLog((className+'.'+methodName), null, new Long(0));
        }
        	
    }

    @AfterReturning("webAccessLog()")
    public void logAccessAfter(JoinPoint joinPoint) {
      /*  System.out.println("Completed: " + joinPoint);
        System.out.println("Before: " + joinPoint);
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        System.out.println("className: " + className);
        System.out.println("methodName: " + methodName);
        loggerServ.insertLog((className+'.'+methodName), null, new Long(0));	*/
    }

}
