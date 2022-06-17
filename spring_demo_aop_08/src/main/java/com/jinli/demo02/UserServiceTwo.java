package com.jinli.demo02;

public class UserServiceTwo implements UserServiceImpl{
    @Override
    public void add() {
        System.out.println("房东2增加一个用户");
    }

    @Override
    public void del() {
        System.out.println("房东2减少一个用户");
    }

    @Override
    public void update() {
        System.out.println("房东2更新一个用户");
    }

    @Override
    public void query() {
        System.out.println("房东2查询一个用户");
    }
}
