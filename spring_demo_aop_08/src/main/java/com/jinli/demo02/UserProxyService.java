package com.jinli.demo02;
//代理
public class UserProxyService implements UserServiceImpl{
    private UserService userservice;

    public UserProxyService(UserService userservice) {
        this.userservice = userservice;
    }
    /**
    public void setUserService(UserService userservice) {
        this.userservice = userservice;
    }
     **/

    public void allMethod(){
        add();
        del();
        update();
        query();
    }

    @Override
    public void add() {
        System.out.println("有房子出租");
    }

    @Override
    public void del() {
        System.out.println("房子已租");
    }

    @Override
    public void update() {
        System.out.println("有新房子出租");
    }

    @Override
    public void query() {
        System.out.println("查看有没有客户要租房子");
    }
}
