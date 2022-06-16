package com.jinli.demo02;
//客户
public class ClientService {
    public static void main(String[] args) {
        //创建一个真实的对象--房东
        UserService userService = new UserService();
        UserProxyService userProxyService = new UserProxyService(userService);
        userProxyService.allMethod();
    }
}
