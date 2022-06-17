package com.jinli.demo04;

import com.jinli.demo02.UserService;
import com.jinli.demo02.UserServiceImpl;


public class Client02 {
    public static void main(String[] args) {
        //真实角色
        UserService userService = new UserService();
        //代理角色--不存在
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        //设置要代理的真实对象
        pih.setTarget(userService);
        //动态生成代理类--接口对象
        UserServiceImpl proxy =(UserServiceImpl) pih.getProxy();
        //这里调用真实对象里面的方法执行，由代理对象去执行的，真实对象不用关心
        proxy.del();
    }
}
