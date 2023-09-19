package com.example.demo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.demo.bean.dto.MeiMeiFileDTO;
import com.example.demo.bean.vo.AddressEntity;
import com.example.demo.bean.vo.FileAddressEntity;
import com.example.demo.constant.Constant;
import com.example.demo.service.MeiMeiFileService;
import com.example.demo.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class MeiMeiFileServiceImpl implements MeiMeiFileService {

    /**
     * 地址解析
     *
     * @param fileDTO
     * @return
     */
    @Override
    public String DownLoadFileByAddress(MeiMeiFileDTO fileDTO) {
        String msg = "";

        try {
            List<FileAddressEntity> addressEntityList = (List<FileAddressEntity>) ExcelUtils.EasyExcelImport(FileAddressEntity.class, fileDTO.getFileInName());
            if (CollectionUtil.isNotEmpty(addressEntityList)) {
                List<AddressEntity> writeList = Lists.newArrayList();
                addressEntityList.stream()
                        .forEach(a -> {
                            AddressEntity addressEntity = new AddressEntity();
                            //id
                            addressEntity.setXuhao(a.getXuhao());
                            //订单号
                            addressEntity.setOrderNum(a.getOrderNum());
                            //菜鸟地址
                            addressEntity.setCnAddress(a.getCnAddress());
                            //菜鸟名称
                            addressEntity.setCnName(a.getCnName());
                            //菜鸟手机号
                            addressEntity.setCnTel(a.getCnTel());

                            String[] addressArr = new String[]{};
                            if (ObjectUtil.isNotEmpty(a) && ObjectUtil.isNotEmpty(a.getCnAddress())) {
                                addressArr = a.getCnAddress().split(" ");
                            }
                            List<String> addressList = Arrays.asList(addressArr);
                            if (CollectionUtil.isNotEmpty(addressList)) {
                                //省
                                addressEntity.setProvince(addressList.size() > 0 ? addressList.get(0) : "");
                                //市
                                addressEntity.setCity(addressList.size() > 1 ? addressList.get(1) : "");
                                //区
                                addressEntity.setArea(addressList.size() > 2 ? addressList.get(2) : "");
                                //街道
                                addressEntity.setStreet(addressList.size() > 3 ? addressList.get(3) : "");
                                //详细地址
                                addressEntity.setAddress(addressList.size() > 4 ? addressList.get(4) : "");
                            }
                            writeList.add(addressEntity);
                        });

                ExcelUtils.EasyExcelOutport((Constant.StringFields.EASYIMPORT_PATH + fileDTO.getFileInName()),
                        false, writeList, fileDTO.getFileOutName(), AddressEntity.class);
                msg = "文件写入完成-->" + Constant.StringFields.EASYOUTPORT_PATH + fileDTO.getFileOutName();
            } else {
                msg = fileDTO.getFileInName() + "模版导入数据为空";
            }
        } catch (Exception e) {
            log.error("DownLoadFileByMeiMei is error: " + e.getMessage());
            msg = e.getMessage();
        }
        return msg;
    }

}
