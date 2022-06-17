package com.jinli.log;

import org.springframework.aop.AfterReturningAdvice;
import java.lang.reflect.Method;

public class AfterLog implements AfterReturningAdvice {
    @Override
    //returnValue：返回值
    public void afterReturning(Object returnValue, Method method, Object[] objects, Object target) throws Throwable {
        System.out.println("返回值是："+returnValue+ "，使用了"+ method.getName() +"的方法");
    }
}
