package com.example.demo.controller;

import com.example.demo.bean.UserBean;
import com.example.demo.bean.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
