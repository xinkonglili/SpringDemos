package com.jinli.demo03;

import com.jinli.demo01.ProxyDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//代理调用处理程序，使用这个类自动生成代理类
public class ProxyInvocationHandler implements InvocationHandler {
    //被代理的接口
    private Rent rent;

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    //生成得到代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),rent.getClass().getInterfaces(),this);
    }

    //使用这个代理类，必须要实现的接口，来处理这个代理类
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        seeHouse();
        //使用反射机制实现
        Object result = method.invoke(rent, args); //introduce variable
        fareHouse();
        return result;
    }

    public void seeHouse(){
        System.out.println("中介带去看房子");
    }

    public void fareHouse(){
        System.out.println("中介收中介费");
    }


}
