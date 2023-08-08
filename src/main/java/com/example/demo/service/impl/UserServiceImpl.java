package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.bean.UserBean;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Boolean add(UserBean userBean) {
        return userMapper.insert(userBean) > 0;
    }

    @Override
    public Boolean del(Integer userId) {
        return userMapper.deleteById(userId) > 0;
    }

    @Override
    public Boolean update(UserBean userBean) {
        return userMapper.updateById(userBean) > 0;
    }

    @Override
    public List<UserBean> findAll() {
        return userMapper.selectList(null);
    }

    @Override
    public UserBean findById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public List<UserBean> findAllByName(UserBean userBean) {
        if(Objects.isNull(userBean)){
            return null;
        }
        QueryWrapper<UserBean> queryWrapper = new QueryWrapper<>();
        if (Strings.isNotBlank(userBean.getUserName())) {
            queryWrapper.like("user_name", userBean.getUserName());
        }
        if (Strings.isNotBlank(userBean.getCardNo())) {
            queryWrapper.eq("card_no", userBean.getCardNo());
        }
        if (Objects.nonNull(userBean.getCreateTime())){
            queryWrapper.ge("create_time", userBean.getCreateTime());
        }
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<UserBean> findInfosByPage(UserBean userBean, Page page) {
        if(Objects.isNull(userBean)){
            return null;
        }
        LambdaQueryWrapper<UserBean> queryWrapper = new LambdaQueryWrapper<UserBean>();
        if (Strings.isNotBlank(userBean.getUserName())) {
            queryWrapper.like(UserBean::getUserName, userBean.getUserName());
        }
        if (Objects.nonNull(userBean.getUserAge())) {
            queryWrapper.gt(UserBean::getUserAge, userBean.getUserAge());
        }
        if (Strings.isNotBlank(userBean.getCardNo())) {
            queryWrapper.eq(UserBean::getCardNo, userBean.getCardNo());
        }
        if (Objects.nonNull(userBean.getCreateTime())){
            queryWrapper.ge(UserBean::getCreateTime, userBean.getCreateTime());
        }
        return userMapper.selectPage(page, queryWrapper);
    }
}
