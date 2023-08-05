package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.bean.UserBean;

import java.util.List;

public interface UserService {

    public Boolean add(UserBean userBean);

    public void insertBatch();

    public Boolean del(Integer userId);

    public Boolean update(UserBean userBean);

    public List<UserBean> findAll();

    public UserBean findById(Integer userId);

    public List<UserBean> findAllByName(UserBean userBean);

    public IPage<UserBean> findInfosByPage(UserBean userBean, Page page);
}
