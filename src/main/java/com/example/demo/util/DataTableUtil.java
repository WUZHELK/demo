package com.example.demo.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import com.example.demo.annotation.DataField;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 表单头信息
 *
 * @author wuzhe
 * @date 2023-9-12 10:50:28
 */
public class DataTableUtil {

    /**
     * 通过传入的Class来获取自定义注解@DataFiled
     * @param cls
     * @return
     */
    public static Map<String, String> getFieldTitle(Class<?> cls) {
        Assert.notNull(cls);
        Map<String, String> tableMap = Maps.newHashMap();
        Field[] fields = ReflectUtil.getFields(cls);

        for (Field field : fields) {
            tableMap.put(field.getAnnotation(DataField.class).name(), field.getAnnotation(DataField.class).title());
        }
        return tableMap;
    }

}
