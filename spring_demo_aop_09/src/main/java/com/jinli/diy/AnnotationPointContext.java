package com.jinli.diy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect  //切面注解是写在类上面的
public class AnnotationPointContext {

    @Before("execution(* com.jinli.service.UserServiceImpl.*(..))") //增加切入点
    public void Before(){
        System.out.println("-----------方法执行前------------");
    }
    @After("execution(* com.jinli.service.UserServiceImpl.*(..))")
    public void after(){
        System.out.println("-----------方法执行后----------");
    }
    @Around("execution(* com.jinli.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("----环绕前-----");

        Signature signature = jp.getSignature();//获得签名：void com.jinli.service.UserService.add()
        System.out.println("signature： "+ signature);

        //执行方法
        Object proceed = jp.proceed();

        System.out.println("----环绕后-----");
    }
}

