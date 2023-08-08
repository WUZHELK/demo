package com.example.demo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.bean.UserBean;
import com.example.demo.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Random;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void testSelect() {
        userService.findById(1);
    }

    @Test
    public void testInsert() {
        int size = 100;
        for (int i = 0; i < size; i++) {
            UserBean userbean = new UserBean();
            userbean.setUserName("test" + i);
            userbean.setPassword(DigestUtils.md5Hex("111111"));
            userbean.setUserSex(i % 2 == 0 ? "1" : "0");
            long t = System.currentTimeMillis();//获得当前时间的毫秒数
            Random rd = new Random(t);
            userbean.setCardNo("4304812000" + String.valueOf(rd.nextInt()).substring(0, 8));
            Integer telNo = Math.abs(rd.nextInt());
            Long tel = Long.parseLong(String.valueOf(telNo).length() < 11
                    ? StringUtils.leftPad(String.valueOf(telNo), 11, "1") : String.valueOf(telNo));
            userbean.setTelNo(tel);
            userbean.setUserAge(String.valueOf(i).length() < 2
                    ? Integer.parseInt(StringUtils.leftPad(String.valueOf(i), 2, "2"))
                    : Integer.parseInt(String.valueOf(i).substring(0, 2)));
            userbean.setCreateBy("wuzhe");
            userbean.setCreateTime(new Date());
            userbean.setModifyNo(0);
            userService.add(userbean);
        }
    }

    @Test
    public void testQueryByPage(){
        UserBean userBean = new UserBean();
        userBean.setUserAge(18);
        Page<UserBean> page = new Page<UserBean>();
        page.setCurrent(1);
        page.setSize(10);
        IPage<UserBean> infosByPage = userService.findInfosByPage(userBean, page);
        System.out.println(infosByPage);
    }

}
