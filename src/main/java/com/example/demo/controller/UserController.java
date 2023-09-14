package com.example.demo.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.example.demo.bean.UserBean;
import com.example.demo.bean.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/select_user")
    public UserBean selectUser() {
        return userService.findById(20);
    }

    @RequestMapping("/insert_user")
    public Boolean insertUser(@RequestBody UserDTO userDTO) {
        UserBean userbean = new UserBean();
        BeanUtils.copyProperties(userDTO, userbean);
        return userService.add(userbean);
    }

    @RequestMapping("/lists_by_name")
    public List<?> selectUsersByName(@Validated @RequestBody UserDTO userDTO){
        UserBean userbean = new UserBean();
        BeanUtils.copyProperties(userDTO, userbean);
        List<UserBean> userLists = userService.findAllByName(userbean);
        if(CollectionUtil.isNotEmpty(userLists)){
            Map<String, List<UserBean>> cardLists = userLists
                    .stream()
                    .parallel()
                    .collect(Collectors.groupingBy(UserBean::getUserSex));
        }
        return null;
    }
}
