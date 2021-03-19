package net.mw.system.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * json操作工具类.
 * 
 * @author 侯骏雄
 * @since 6.0.0
 */
public final class JsonHelper {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(JsonHelper.class
            .getName());
    
    /**
     * jackson映射器.
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * json操作工具类的私有构造方法，其作用是为了防止用户显式生成工具类的实例对象.
     * 
     */
    private JsonHelper() {
    }

    /**
     * 将json字符串转换为指定javabean的列表.
     * 
     * @param json
     *            需要转换的json字符串
     * @param beanClass
     *            javabean类对象
     * @param <T>
     *            javabean类型
     * @return 转换后的Javabean列表
     */
    public static <T> List<T> jsonToBeanList(final String json,
            final Class<T> beanClass) {
        logger.trace("进入jsonToBeanList方法");
        List<T> list = new ArrayList<T>();
        StringBuffer jsonBuffer = new StringBuffer(json.trim());
        jsonBuffer.deleteCharAt(jsonBuffer.indexOf("["));
        jsonBuffer.deleteCharAt(jsonBuffer.lastIndexOf("]"));
        String tempJson = jsonBuffer.toString();
        if (!"".equals(tempJson.trim())) {
            String[] beanJson = tempJson.split("},");
            for (String s : beanJson) {
                if (s.charAt(s.length() - 1) != '}') {
                    s = s + "}";
                }
                list.add(jsonToBean(s, beanClass));
            }
        }
        logger.trace("退出jsonToBeanList方法");
        return list;
    }

    /**
     * 将json字符串转换为指定javabean.
     * 
     * @param json
     *            需要转换的json字符串
     * @param beanClass
     *            javabean类对象
     * @param <T>
     *            javabean类型
     * @return 转换后的Javabean
     */
    public static <T> T jsonToBean(final String json, final Class<T> beanClass) {
        logger.trace("进入jsonToBean方法");
        T bean = null;
        try {
            bean = mapper.readValue(json, beanClass);
        } catch (Exception e) {
            logger.error("Exception:", e);
            e.printStackTrace();
        }
        logger.trace("退出jsonToBean方法");
        return bean;
    }

    /**
     * 将json字符串转换为map键值对.
     * 
     * @param json
     *            需要转换的json字符串
     * @return 将存有json内容的键值对map
     */
    public static Map<String, Object> jsonToMap(final String json) {
        logger.trace("进入jsonToMapForSina方法");
        Map<String, Object> map = null;
        try {
        	map =  mapper.readValue(json, HashMap.class);
        } catch (Exception e) {
            logger.error("Exception:", e);
            e.printStackTrace();
        }

        logger.trace("退出jsonToMapForSina方法");
        return map;
    }

    /**
     * 将object转换为json字符串.
     * 
     * @param obj
     *            需要转换的对象
     * @return json字符串
     */
    public static String toJson(final Object obj) {
        logger.trace("进入toJson方法");
        String json = null;
        try {
        	json = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("Exception:", e);
            e.printStackTrace();
        }

        logger.trace("退出toJson方法");
        return json;
    }
}
