package com.example.demo.controller;

import com.example.demo.bean.dto.MeiMeiFileDTO;
import com.example.demo.service.MeiMeiFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meimei")
public class FileController {

    @Autowired
    private MeiMeiFileService meiMeiFileService;

    /**
     * 根据地址解析出具体文件信息
     *
     * @param fileDTO
     * @return
     */
    @RequestMapping("/download_file/by_address")
    public String createFile(@Validated @RequestBody MeiMeiFileDTO fileDTO) {
        return meiMeiFileService.DownLoadFileByAddress(fileDTO);
    }
}
