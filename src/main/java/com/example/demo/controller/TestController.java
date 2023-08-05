package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
public class TestController {

    @RequestMapping("/")
    public String getTestInfo(){
        return testConnection() ? "Hello World!" : "Failure";
    }

    public boolean testConnection() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8083/org_wuzhe?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&useSSL=false", "root", "123mysql")) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
