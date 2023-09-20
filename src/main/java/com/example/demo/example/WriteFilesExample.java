package com.example.demo.example;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.example.demo.bean.dto.FileDTO;
import com.example.demo.constant.Constant;
import com.example.demo.util.FileUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
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
import java.util.stream.Collectors;

@Slf4j
public class WriteFilesExample {

    // 每日卡片激活文件
    private static final Map<String, String> map1 = Maps.newHashMap();

    static {
        map1.put("index", "D:\\Test\\index.txt");
        map1.put("flag", "每日卡片激活文件");
        map1.put("fileName", "10231990000_NGCC-ACTIVEDCARD_0000_20350813_A_0001_0001_##");
        map1.put("filePath", "NGCC-ACTIVEDCARD");
    }

    // 自动续卡审核检查失败卡片列表（审核阶段）
    private static final Map<String, String> map2 = Maps.newHashMap();

    static {
        map2.put("index", "D:\\Test\\index2.txt");
        map2.put("flag", "自动续卡审核检查失败卡片列表（审核阶段）");
        map2.put("fileName", "10231990000_NGCC-CDRISR_ERR_0000_20350813_A_0001_0001_####");
        map2.put("filePath", "NGCC-CDRISR_ERR");
    }

    // 自动续卡审核检查失败卡片列表(制卡阶段)
    private static final Map<String, String> map3 = Maps.newHashMap();

    static {
        map3.put("index", "D:\\Test\\index3.txt");
        map3.put("flag", "自动续卡审核检查失败卡片列表(制卡阶段)");
        map3.put("fileName", "10231990000_NGCC-CDRISP_ERR_0000_20350813_A_0001_0001_####");
        map3.put("filePath", "NGCC-CDRISP_ERR");
    }

    // 自动续卡检查通过卡片列表（审核阶段）
    private static final Map<String, String> map4 = Maps.newHashMap();

    static {
        map4.put("index", "D:\\Test\\index4.txt");
        map4.put("flag", "自动续卡检查通过卡片列表（审核阶段）");
        map4.put("fileName", "10231990000_NGCC-CDRISR_RST_0000_20350813_A_0001_0001_####");
        map4.put("filePath", "NGCC-CDRISR_RST");
    }

    // 自动续卡检查通过卡片列表（制卡阶段）
    private static final Map<String, String> map5 = Maps.newHashMap();

    static {
        map5.put("index", "D:\\Test\\index5.txt");
        map5.put("flag", "自动续卡检查通过卡片列表（制卡阶段）");
        map5.put("fileName", "10231990000_NGCC-CDRISP_RST_0000_20350813_A_0001_0001_####");
        map5.put("filePath", "NGCC-CDRISP_RST");
    }

    // NOTES记录批量导入文件接口
    private static final Map<String, String> map6 = Maps.newHashMap();

    static {
        map6.put("cardPath", "D:\\Test\\cardList.txt");
        map6.put("index", "D:\\Test\\index6.txt");
        map6.put("flag", "NOTES记录批量导入文件接口");
        map6.put("fileName", "99711290000_NGCC-DZ-NOTES_0000_yyyyMMdd_A_0001_0001");
        map6.put("filePath", "NGCC-DZ-NOTES");
    }

    public static final Map<String, Map<String, String>> fileMap = Maps.newHashMap();

    static {
        fileMap.put("每日卡片激活文件", map1);
        fileMap.put("自动续卡审核检查失败卡片列表（审核阶段）", map2);
        fileMap.put("自动续卡审核检查失败卡片列表(制卡阶段)", map3);
        fileMap.put("自动续卡检查通过卡片列表（审核阶段）", map4);
        fileMap.put("自动续卡检查通过卡片列表（制卡阶段）", map5);
        fileMap.put("NOTES记录批量导入文件接口", map6);
    }

    private static List<String> getCardListByFile(String cardPath){
        List<String> resultList = Lists.newArrayList();
        // 文件基本信息
        List<String> cardList = FileUtils.readFile(cardPath);
        log.info("cardList---->" + cardList.size());
        if(CollectionUtil.isNotEmpty(cardList)){
            resultList = cardList.stream().distinct().collect(Collectors.toList());
            log.info("resultList---->" + resultList.size());
        }
        return resultList;
    }

    public static String getFileName(String index, String fileName) {
        Integer indexLength = (1 + (fileName.lastIndexOf("#") - fileName.indexOf("#")));
        index = StringUtils.leftPad(index, indexLength, "0");
        String startLength = fileName.substring(0, fileName.indexOf("#"));
        String endLength = fileName.substring((1 + fileName.lastIndexOf("#")));
        fileName = startLength + index + endLength;
        return fileName;
    }

    public static String getFileName2(String fileName) {
        String dataTime = DateUtil.format(new Date(), DateTimeFormatter.ofPattern("yyyyMMdd"));
        return fileName.replace("yyyyMMdd", dataTime);
    }

    private static Boolean test(FileDTO fileDTO) throws InterruptedException {
        // 文件数据基本信息
        String delimiter = fileDTO.getDelimiter();
        String endLimiter = fileDTO.getEndLimiter();
        Integer primaryIndex = fileDTO.getPrimaryIndex();
        String fileName = fileDTO.getFileName();
        // 文件中数据量
        Integer writeNum = fileDTO.getWriteNum();
        // 需要生成的总文件数
        Integer totalSize = fileDTO.getTotalSize();

        Map<String, String> map = fileMap.get(fileName);
        String indexInfoPath = map.get("index");

        // 文件基本信息
        List<String> indexList = FileUtils.readFile(indexInfoPath);

        if(map6.get("flag").equals(fileName)){
            List<String> cardList = getCardListByFile(map.get("cardPath"));
            fileName = getFileName2(map.get("fileName")) + Constant.StringFields.FILE_POST_FIX;
            String filePath = Constant.StringFields.LOCAL_PATH + Constant.StringFields.FILE_MENU_SIGN + map.get("filePath");
            test1(delimiter, endLimiter, cardList, fileName, filePath, indexList, primaryIndex);
        }
        else{
            for (int i = 1; i <= totalSize; i++) {
                fileName = getFileName(Integer.toString(i), map.get("fileName")) + Constant.StringFields.FILE_POST_FIX;
                String filePath = Constant.StringFields.LOCAL_PATH + Constant.StringFields.FILE_MENU_SIGN + map.get("filePath");
                test1(delimiter, endLimiter, writeNum, Integer.toString(i), fileName, filePath, indexList, primaryIndex);
            }
        }

        return true;
    }

    /**
     * 生成文件内容并写入
     *
     * @param delimiter
     * @param endLimiter
     * @param writeNum
     * @param totalNum
     * @param fileName
     * @param filePath
     * @param indexList
     * @param pkIndex
     * @return
     */
    public static Boolean test1(String delimiter, String endLimiter, Integer writeNum, String totalNum, String fileName,
                                String filePath, List<String> indexList, Integer pkIndex) {
        Boolean resultFlag = false;
        String dataTime = DateUtil.format(new Date(), DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuilder sb = new StringBuilder();
        totalNum = (totalNum.indexOf("0") > -1 ? (totalNum + "1") : totalNum);
        for (int i = 1; i <= writeNum; i++) {
            for (int j = 0; j < indexList.size(); j++) {
                String e = indexList.get(j);
                Integer index = Integer.parseInt(e);
                if (j == pkIndex) {
                    sb.append(dataTime)
                            .append(totalNum)
                            .append((StringUtils.leftPad(Integer.toString(i),
                                (index - dataTime.length() - totalNum.length()),
                                "0")));
                } else {
                    String msg = UUID.randomUUID().toString().replace("-", "");
                    msg = msg.length() > index ? msg.substring(0, index) : StringUtils.leftPad(msg, index, msg);
                    sb.append(msg);
                }

                // 最后一个
                if (j >= indexList.size() - 1) {
                    sb.append(endLimiter);
                } else {
                    sb.append(delimiter);
                }
            }
            if (i != writeNum) {
                sb.append("\n");
            }
        }
        if (FileUtils.uploadFile(sb.toString(), filePath, fileName)) {
            log.info("write success");
            resultFlag = true;
        }
        return resultFlag;
    }


    /**
     * 生成文件内容并写入
     *
     * @param delimiter
     * @param endLimiter
     * @param cardList
     * @param fileName
     * @param filePath
     * @param indexList
     * @param pkIndex
     * @return
     */
    public static Boolean test1(String delimiter, String endLimiter, List<String> cardList, String fileName,
                                String filePath, List<String> indexList, Integer pkIndex) {
        Boolean resultFlag = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= cardList.size(); i++) {
            for (int j = 0; j < indexList.size(); j++) {
                String e = indexList.get(j);
                Integer index = Integer.parseInt(e);
                if (j == pkIndex) {
                    sb.append(cardList.get(i-1));
                } else {
                    String msg = UUID.randomUUID().toString().replace("-", "");
                    msg = msg.length() > index ? msg.substring(0, index) : StringUtils.leftPad(msg, index, msg);
                    sb.append(msg);
                }

                // 最后一个
                if (j >= indexList.size() - 1) {
                    sb.append(endLimiter);
                } else {
                    sb.append(delimiter);
                }
            }
            if (i != cardList.size()) {
                sb.append("\n");
            }
        }
        if (FileUtils.uploadFile(sb.toString(), filePath, fileName)) {
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
