package com.jinli.demo01;

public class Proxy implements Rent{
    private Host host;

    public Proxy() {
    }
   //房东有多个,调用有参构造
    public Proxy(Host host) {
        this.host = host;
    }

    @Override
    public void rent() {
        //通过代理去租房子，其实还是在走房东去租房子
        //传进来哪个房东，就给哪个房东租房子
        host.rent();
        seeHouse();
        contract();
        receiveHouseMoney();
    }
    //看房子
    public void seeHouse(){
        System.out.println("中介带你看房子");
    }

    //签合同contract
    public void contract(){
        System.out.println("和中介签合同");
    }

    //收中介费
    public void receiveHouseMoney(){
        System.out.println("收取中介费");
    }
}
