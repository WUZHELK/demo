package com.example.demo.controller;

import com.example.demo.bean.dto.FileDTO;
import com.example.demo.bean.vo.User;
import com.example.demo.example.WriteFilesExample;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

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

    @RequestMapping("/test_esensitized")
    public User testDesensitization(){
        User user = User.builder().
                idCard("188888888888888888").
                phone("18888888888").
                bankCard("188888888888").
                address("北京市朝阳区").build();
        System.out.println(user);
        return user;
    }

    @RequestMapping("/write_file")
    public Boolean createFile(@Validated @RequestBody FileDTO fileDTO) throws InterruptedException, ExecutionException {
        return WriteFilesExample.writeFileByName(fileDTO);
    }
}
