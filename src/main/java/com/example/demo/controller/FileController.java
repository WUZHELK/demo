package com.example.demo.controller;

import com.example.demo.bean.dto.MeiMeiFileDTO;
import com.example.demo.util.ExcelUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.HashMap;

@RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/download_file")
    public String createFile(@RequestBody HashMap map) throws FileNotFoundException {
        return ExcelUtils.DownLoadFileByMeiMei(map);
    }
}
