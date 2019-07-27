package com.amir.product.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyq on 2017/12/4.
 */
public class JSONUtils {
    private static final Log log = LogFactory.getLog(JSONUtils.class);

    public JSONUtils() {
    }

    public static String toJSON(Object object) {
        return JSONObject.toJSONString(object);
    }

    public static String toJSONWithArray(Object object) {
        return JSONArray.toJSONString(object);
    }

    public static Map<String, Object> toMap(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        try {
            return (Map)objectMapper.readValue(json, Map.class);
        } catch (JsonParseException var3) {
            var3.printStackTrace();
        } catch (JsonMappingException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return null;
    }

    public static List<Map<String, Object>> toList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        try {
            return (List)objectMapper.readValue(json, List.class);
        } catch (JsonParseException var3) {
            var3.printStackTrace();
        } catch (JsonMappingException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return null;
    }

    public static <T> T toBean(String content, Class<T> targetClass) {
        if(StringUtils.isEmpty(content)) {
            return null;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                return objectMapper.readValue(content, targetClass);
            } catch (JsonParseException var4) {
                log.info("JSON parse to " + targetClass.getSimpleName() + " occur JsonParseExcetion. content:" + content, var4);
            } catch (JsonMappingException var5) {
                log.info("JSON parse to " + targetClass.getSimpleName() + " occur JsonMappingException. content:" + content, var5);
            } catch (IOException var6) {
                log.info("JSON parse to " + targetClass.getSimpleName() + " occur IOException. content:" + content, var6);
            }

            return null;
        }
    }
}
