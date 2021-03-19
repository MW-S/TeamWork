package net.mw.system.utils;

/**
 * Copyright © 2019 广州市麒骏网络科技有限公司版权所有.
 * 请勿修改或删除版权声明及文件头部.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;


/**
 * null对象工具类.
 * 
 * @author 侯骏雄
 * @since 6.0.0
 */
public final class NullHelper {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(NullHelper.class
            .getName());

    /**
     * 私有构造方法，其作用是为了防止用户显式生成工具类的实例对象.
     * 
     */
    private NullHelper() {
    }

    /**
     * 获取指定类型null对象.
     * 
     * @param clazz
     *            获取的null类型.
     * @param <T>
     *            null类型.
     * @return null类型对象.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getNullObject(final Class<T> clazz) {
        logger.trace("进入getNullObject方法");
        T result = null;

        Class<NullConstant> nullConstant = NullConstant.class;
        Field[] nullObjects = nullConstant.getFields();
        for (int i = 0; i < nullObjects.length; i++) {
            if (clazz.isAssignableFrom(nullObjects[i].getType())) {
                try {
                    result = (T) nullObjects[i].get(nullConstant);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        logger.trace("退出getNullObject方法");
        return result;
    }

    /**
     * 判断对象是否代表null.
     * 
     * @param obj
     *            待判断是否代表null的对象.
     * @param <T>
     *            对象类型.
     * @return true 该对象代表null false 该对象不代表null.
     */
    public static <T> Boolean isNull(final T obj) {
        logger.trace("进入isNull方法");
        Boolean result = false;
        Class<? extends Object> clazz = obj.getClass();

        Class<NullConstant> nullConstant = NullConstant.class;
        Field[] nullObjects = nullConstant.getFields();
        for (int i = 0; i < nullObjects.length; i++) {
            if (clazz.isAssignableFrom(nullObjects[i].getType())) {
                try {
                    result = nullObjects[i].get(nullConstant) == obj;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        logger.trace("退出isNull方法");
        return result;
    }
}

