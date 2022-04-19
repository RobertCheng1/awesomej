package com.company.utils;

import com.company.Student;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.MapType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: JacksonUtils
 * @Date: 2022/3/23 17:02
 */
public class JacksonUtils {


    public void bean2Map() {
        // 这是来自 StarDB cluster 项目的逻辑
        try {
            Student tmp = new Student("robert", 22, 99);
            ObjectMapper MAPPER = new ObjectMapper()
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            String jsonStr = MAPPER.writeValueAsString(tmp);
            System.out.printf("jsonStr = %s\n", jsonStr);

            MapType TYPE_MAP = MAPPER.getTypeFactory().constructMapType(HashMap.class, Object.class, Object.class);
            Map<String, Object> after = MAPPER.readValue(jsonStr, TYPE_MAP);
            for (String key : after.keySet()) {
                System.out.printf("key = %s\n", key);
            }
        }  catch (final IOException ex) {
            System.out.println(ex);
        }
    }
}
