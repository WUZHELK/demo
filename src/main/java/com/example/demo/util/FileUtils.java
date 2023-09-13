package com.example.demo.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 文件工具类
 *
 * @author longkai
 * @date 2023-9-13 09:22:21
 *
 */
@Slf4j
public class FileUtils {

    private static final String localPath = "D:\\Test";

    private static final String fileName = "yyyyMMdd_HHmmss";

    private static final String filePostfix = ".txt";

    /**
     * 通过文件信息写入到默认的文件中
     * @param content
     * @return
     */
    public static boolean uploadFile(String content){
        return uploadFile(content, localPath,
                DateUtil.format(new Date(), DateTimeFormatter.ofPattern(fileName)) + filePostfix);
    }

    /**
     * 自定义文件信息写入
     *
     * @param content
     * @param localFilePath
     * @param selfFileName
     * @return
     */
    public static boolean uploadFile(String content, String localFilePath, String selfFileName){
        try {
            if(StringUtils.isBlank(selfFileName)){
                selfFileName = DateTimeFormatter.ofPattern(fileName) + filePostfix;
            }
            if(!FileUtil.isDirectory(localFilePath)){
                FileUtil.mkdir(localFilePath);
            }

            FileUtil.appendUtf8String(content, new File(localFilePath, selfFileName));

            return FileUtil.isNotEmpty(FileUtil.newFile(localFilePath + "/" + selfFileName));
        }catch (Exception e){
            log.error("FileUtils exist error: " + e);
        }
        return false;
    }

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder("文件信息：");
        stringBuilder.append(System.getProperty("line.separator"))
                .append("随便写的信息")
                .append(System.getProperty("line.separator"))
                .append("署名人：wuzhe");
        uploadFile(stringBuilder.toString());
    }

}
