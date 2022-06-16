package com.jinli.demo02;

//真实对象
public class UserService implements UserServiceImpl {


    @Override
    public void add() {
        System.out.println("增加一个用户");
    }

    @Override
    public void del() {
        System.out.println("删除一个用户");
    }

    @Override
    public void update() {
        System.out.println("修改一个用户");
    }

    @Override
    public void query() {
        System.out.println("查询一个用户");
    }
}
