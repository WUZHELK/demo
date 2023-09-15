package com.example.demo.example;

import cn.hutool.core.date.DateUtil;
import com.example.demo.bean.dto.FileDTO;
import com.example.demo.constant.Constant;
import com.example.demo.util.FileUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class WriteFilesExample {

    // 每日卡片激活文件
    private static final Map<String, String> map1 = Maps.newHashMap();

    static {
        map1.put("index", "D:\\Test\\index.txt");
        map1.put("flag", "每日卡片激活文件");
    }

    // 自动续卡审核检查失败卡片列表（审核阶段）
    private static final Map<String, String> map2 = Maps.newHashMap();

    static {
        map2.put("index", "D:\\Test\\index2.txt");
        map2.put("flag", "自动续卡审核检查失败卡片列表（审核阶段）");
    }

    // 自动续卡审核检查失败卡片列表(制卡阶段)
    private static final Map<String, String> map3 = Maps.newHashMap();

    static {
        map3.put("index", "D:\\Test\\index3.txt");
        map3.put("flag", "自动续卡审核检查失败卡片列表(制卡阶段)");
    }

    // 自动续卡检查通过卡片列表（审核阶段）
    private static final Map<String, String> map4 = Maps.newHashMap();

    static {
        map4.put("index", "D:\\Test\\index4.txt");
        map4.put("flag", "自动续卡检查通过卡片列表（审核阶段）");
    }

    // 自动续卡检查通过卡片列表（制卡阶段）
    private static final Map<String, String> map5 = Maps.newHashMap();

    static {
        map5.put("index", "D:\\Test\\index5.txt");
        map5.put("flag", "自动续卡检查通过卡片列表（制卡阶段）");
    }

    public static final Map<String, Map<String, String>> fileMap = Maps.newHashMap();

    static {
        fileMap.put("每日卡片激活文件", map1);
        fileMap.put("自动续卡审核检查失败卡片列表（审核阶段）", map2);
        fileMap.put("自动续卡审核检查失败卡片列表(制卡阶段)", map3);
        fileMap.put("自动续卡检查通过卡片列表（审核阶段）", map4);
        fileMap.put("自动续卡检查通过卡片列表（制卡阶段）", map5);
    }

    private static Boolean test(FileDTO fileDTO) throws InterruptedException {
        // 文件数据基本信息
        String startLimiter = fileDTO.getStartLimiter();
        String endLimiter = fileDTO.getEndLimiter();
        Integer writeNum = fileDTO.getWriteNum();
        Integer primaryIndex = fileDTO.getPrimaryIndex();
        String fileName = fileDTO.getFileName();

        Map<String, String> map = fileMap.get(fileName);
        String indexInfoPath = map.get("index");
        // 文件基本信息
        List<String> indexList = FileUtils.readFile(indexInfoPath);
        fileName = fileDTO.getFileName() + DateUtil.format(new Date(), DateTimeFormatter.ofPattern("yyyyMMdd"))
                + Constant.StringFields.FILE_POST_FIX;
        return test1(startLimiter, endLimiter, writeNum, fileName, indexList, primaryIndex);
    }

    /**
     * 生成文件内容并写入
     *
     * @param startLimiter
     * @param endLimiter
     * @param writeNum
     * @param fileName
     * @param indexList
     * @param pkIndex
     * @return
     */
    public static Boolean test1(String startLimiter, String endLimiter, Integer writeNum, String fileName,
                                List<String> indexList, Integer pkIndex) {
        Boolean resultFlag = false;
        String dataTime = DateUtil.format(new Date(), DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= writeNum; i++) {
            for (int j = 0; j < indexList.size(); j++) {
                String e = indexList.get(j);
                Integer index = Integer.parseInt(e);
                if (j == pkIndex) {
                    sb.append(dataTime).append((StringUtils.leftPad(Integer.toString(i), (index - 8), "0")));
                } else {
                    String msg = UUID.randomUUID().toString().replace("-", "");
                    msg = msg.length() > index ? msg.substring(0, index) : StringUtils.leftPad(msg, index, msg);
                    sb.append(msg);
                }

                // 最后一个
                if (j >= indexList.size() - 1) {
                    sb.append(endLimiter);
                } else {
                    sb.append(startLimiter);
                }
            }
            if (i != writeNum) {
                sb.append("\n");
            }
        }
        if (FileUtils.uploadFile(sb.toString(), fileName)) {
            log.info("write success");
            resultFlag = true;
        }
        return resultFlag;
    }

    /**
     * 每日卡片激活文件-map1
     * 自动续卡审核检查失败卡片列表（审核阶段）-map2
     * 自动续卡审核检查失败卡片列表(制卡阶段)-map3
     * 自动续卡检查通过卡片列表（审核阶段）-map4
     * 自动续卡检查通过卡片列表（制卡阶段）-map5
     *
     * @param fileDTO
     */
    public static Boolean writeFileByName(FileDTO fileDTO) throws ExecutionException, InterruptedException {
        log.info("start");

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Boolean> future = executorService.submit(() -> {
            Boolean resultFlag = false;
            try {
                resultFlag = test(fileDTO);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return resultFlag;
        });

        executorService.shutdown();
        log.info("resultFlag：" + future.get());
        return future.get();
    }

}
