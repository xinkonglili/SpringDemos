package com.jinli.demo03;

public class Client01 {
    public static void main(String[] args) {
        //真实角色描写
        Host host = new Host();
        //代理角色--现在没有，我们要通过代理程序生成一个代理类
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        //调用处理程序角色来处理我们要调用的接口
        pih.setRent(host);
        //这里的proxy就是动态生成的，我们并没有写
        Rent proxy = (Rent) pih.getProxy();
        proxy.rent();


    }
}
