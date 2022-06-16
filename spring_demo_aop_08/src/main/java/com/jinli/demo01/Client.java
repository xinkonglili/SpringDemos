package com.jinli.demo01;

public class Client {
    public static void main(String[] args) {
        Host host = new Host();
        //代理--把房东的信息给代理,代理会有一些附属的操作
        ProxyDemo proxy = new ProxyDemo(host);  //调用有参构造
        //不用面对房东，找中介租房
        proxy.rent();
    }
}
