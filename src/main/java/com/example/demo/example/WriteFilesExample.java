package com.example.demo.example;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.example.demo.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class WriteFilesExample {

    public static void main(String[] args) {
        log.info("start");
        System.currentTimeMillis();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                test();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    private static void test() throws InterruptedException {
        String dataTime = DateUtil.format(new Date(), DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<String> indexList = FileUtils.readFile("D:\\Test\\index.txt");

        List<String> dataList = FileUtils.readFile("D:\\Test\\data.txt");
        StringBuilder sb = new StringBuilder();
        if(CollectionUtil.isNotEmpty(dataList)){
            String delimiter = dataList.get(0);
            Integer dataSize = Integer.parseInt(dataList.get(1));
            for (int i = 1; i <= dataSize; i++){
                sb.append(dataTime).append((StringUtils.leftPad(Integer.toString(i), 8, "0"))).append(delimiter);
                indexList.stream().forEach(e -> {
                    Integer index = Integer.parseInt(e);
                    String msg = UUID.randomUUID().toString().replace("-", "");
                    msg = msg.length() > index ? msg.substring(0, index) : StringUtils.leftPad(msg, index, msg);
                    sb.append(msg).append(delimiter);
                });
                if(i != dataSize){
                    sb.append("\r");
                }
            }
        }

        if(FileUtils.uploadFile(sb.toString())){
            log.info("write success");
        }

    }

}
