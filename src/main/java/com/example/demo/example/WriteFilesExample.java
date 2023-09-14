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
//                test("D:\\Test\\index.txt", "D:\\Test\\data.txt", "每日卡片激活文件");
                test("D:\\Test\\index2.txt", "D:\\Test\\data2.txt", "自动续卡审核检查失败卡片列表（审核阶段）");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    private static void test(String fileInfoPath, String dataInfoPath, String flag) throws InterruptedException {
        // 文件基本信息
        List<String> indexList = FileUtils.readFile(fileInfoPath);
        // 文件数据基本信息
        List<String> dataList = FileUtils.readFile(dataInfoPath);

        if (CollectionUtil.isNotEmpty(dataList)) {
            if ("每日卡片激活文件".equals(flag)) {
                test1(dataList, indexList, 0);
            } else if ("自动续卡审核检查失败卡片列表（审核阶段）".equals(flag)) {
                test1(dataList, indexList, 0);
            } else if ("自动续卡审核检查失败卡片列表(制卡阶段)".equals(flag)) {

            } else if ("自动续卡检查通过卡片列表（审核阶段）".equals(flag)) {

            } else if ("自动续卡检查通过卡片列表（制卡阶段）".equals(flag)) {

            }

        }


    }

    /**
     * 每日卡片激活文件
     *
     * @param dataList
     * @param indexList
     * @param pkIndex
     */
    public static void test1(List<String> dataList, List<String> indexList, Integer pkIndex){
        String dataTime = DateUtil.format(new Date(), DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuilder sb = new StringBuilder();
        String delimiter = dataList.get(0);
        Integer dataSize = Integer.parseInt(dataList.get(1));
        String endlimiter = dataList.get(2);
        String fileName = dataList.get(3);
        for (int i = 1; i <= dataSize; i++){

            for(int j = 0; j < indexList.size(); j++){
                String e = indexList.get(j);
                Integer index = Integer.parseInt(e);
                if(j == pkIndex){
                    sb.append(dataTime).append((StringUtils.leftPad(Integer.toString(i), 11, "0")));
                }else{
                    String msg = UUID.randomUUID().toString().replace("-", "");
                    msg = msg.length() > index ? msg.substring(0, index) : StringUtils.leftPad(msg, index, msg);
                    sb.append(msg);
                }

                // 最后一个
                if(j >= indexList.size() - 1){
                    sb.append(endlimiter);
                }else{
                    sb.append(delimiter);
                }
            }
            if(i != dataSize){
                sb.append("\n");
            }
        }
        if(FileUtils.uploadFile(sb.toString(), fileName)){
            log.info("write success");
        }
    }

}
