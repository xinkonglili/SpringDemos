package com.jinli.service;

public class UserServiceImpl implements UserService{
    @Override
    public void add() {
        System.out.println("增加了一个user");
    }

    @Override
    public void del() {
        System.out.println("delete了一个 user");
    }

    @Override
    public void update() {
        System.out.println("修改了一个 user");
    }

    @Override
    public void query() {
        System.out.println("查询了一个user");
    }
}
