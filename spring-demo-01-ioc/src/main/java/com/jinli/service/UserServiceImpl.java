package com.jinli.service;

import com.jinli.dao.UserDao;

public class UserServiceImpl implements UserService{
    private UserDao userdao;
    //利用set实现动态注入

    public void setUserDao(UserDao userdao) {
        this.userdao = userdao;
    }

    public void getUser() {
        userdao.getUser();
    }
}
