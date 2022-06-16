package com.jinli.demo03;

import com.jinli.demo01.Rent;

//房东--真实的角色
public class Host implements com.jinli.demo03.Rent { //同名的接口要精确到包，不然会报错

    @Override
    public void rent() {
        System.out.println("房东要出租房子");
    }
}
