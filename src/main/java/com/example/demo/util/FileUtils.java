package com.example.demo.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import com.example.demo.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 文件工具类
 *
 * @author wuzhe
 * @date 2023-9-13 09:22:21
 */
@Slf4j
public class FileUtils {

    /**
     * 通过文件信息写入到默认的文件中
     *
     * @param content
     * @return
     */
    public static boolean uploadFile(String content, String fileName) {
        return uploadFile(content, Constant.StringFields.LOCAL_PATH,
                fileName, "UTF_8");
    }

    /**
     * 自定义文件信息写入
     *
     * @param content
     * @param localFilePath
     * @param selfFileName
     * @return
     */
    public static boolean uploadFile(String content, String localFilePath, String selfFileName, String charset) {
        try {
            if (StringUtils.isBlank(selfFileName)) {
                selfFileName = DateTimeFormatter.ofPattern(Constant.StringFields.LOCAL_PATH)
                        + Constant.StringFields.FILE_POST_FIX;
            }
            if (!FileUtil.isDirectory(localFilePath)) {
                FileUtil.mkdir(localFilePath);
            }
            if(FileUtil.exist(FileUtil.newFile(localFilePath + Constant.StringFields.FILE_MENU_SIGN + selfFileName))){
                FileUtil.del(FileUtil.newFile(localFilePath + Constant.StringFields.FILE_MENU_SIGN + selfFileName));
            }

            if("UTF_8".equals(charset)){
                FileUtil.appendUtf8String(content, new File(localFilePath, selfFileName));
            }else{
                FileUtil.appendString(content, new File(localFilePath, selfFileName), CharsetUtil.CHARSET_GBK);
            }

            return FileUtil.isNotEmpty(FileUtil.newFile(localFilePath
                    + Constant.StringFields.FILE_MENU_SIGN
                    + selfFileName));
        } catch (Exception e) {
            log.error("FileUtils exist error: " + e);
        }
        return false;
    }

    public static List<String> readFile(String filePath) {
        File file = new File(filePath);
        //从文件中读取每一行的UTF-8编码数据
        List<String> readUtf8Lines = FileUtil.readLines(file, CharsetUtil.CHARSET_UTF_8);
        return readUtf8Lines;
    }

}
