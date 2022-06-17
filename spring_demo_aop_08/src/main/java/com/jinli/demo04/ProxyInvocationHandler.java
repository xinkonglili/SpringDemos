package com.jinli.demo04;

import com.jinli.demo03.Rent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//代理调用处理程序，使用这个类自动生成代理类
public class ProxyInvocationHandler implements InvocationHandler {
    //1、被代理的接口
    private Object target;
    public void setTarget(Object target) {
        this.target = target;
    }

    //2、生成得到代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    //3、使用这个代理类，必须要实现的接口，来处理这个代理类
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log(method.getName());
        //使用反射机制实现
        Object result = method.invoke(target, args); //introduce variable
        return result;
    }

    public void log(String method){
        System.out.println("使用了"+method+"方法");
    }
}
