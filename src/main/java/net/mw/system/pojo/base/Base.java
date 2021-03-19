package net.mw.system.pojo.base;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import net.mw.system.utils.NullHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Base {
	
	 /**
     * 时间转换的模式.
     */
    private static final String DEFAULT_PARSE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 内容为null的字符串.
     */
    public static final String NULL_STRING = "null";
	
	/**
     * 返回转换后的参数.
     * 
     * @param obj
     *            成员变量
     * @param castClass
     *            转换类型
     * @return 指定类型参数
     * 
     * @author 侯骏雄
     * @since 6.0.0
     */
    private static Object castVoParam(final Object obj, final Class<?> castClass) {
        Object castParm = null;
        try {
            if (NULL_STRING.equals(obj)) {
                castParm = NullHelper.getNullObject(castClass);
            } else {
                if (castClass.isAssignableFrom(Timestamp.class)) {
                    final Date date = DateUtils.parseDate(String.valueOf(obj),
                            DEFAULT_PARSE_PATTERN);
                    castParm = new Timestamp(date.getTime());
                } else if (castClass.isAssignableFrom(String.class)) {
                    castParm = obj;
                } else {
                    castParm = castClass.getMethod("valueOf", String.class)
                            .invoke(castClass, obj);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return castParm;
    }

    /**
     * 返回转换后的参数.
     * 
     * @param obj
     *            成员变量
     * @return string类型参数
     * 
     * @author 侯骏雄
     * @since 6.0.0
     */
    private static Object castPoParam(final Object obj) {
        Object castParm = null;
        try {
            final Class<?> objClass = obj.getClass();
            if (objClass.isAssignableFrom(Timestamp.class)) {
                castParm = DateFormatUtils.formatUTC((Date) obj,
                        DEFAULT_PARSE_PATTERN);
            } else if (objClass.isAssignableFrom(String.class)) {
                castParm = obj;
            } else {
                castParm = String.valueOf(obj);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return castParm;
    }

    /**
     * 将值对象转换为持久对象.
     * 
     * @param poClass
     *            持久类对象
     * @param <T>
     *            持久类对象类型
     * @return 转换后的持久对象.
     * 
     * @author 侯骏雄
     * @since 6.0.0
     */
    public <T> T voToPo(final Class<T> poClass) {
        T poObj = null;
        try {
            final BeanMap voMap = new BeanMap(this);
            final List<?> tempList = new ArrayList<Object>(voMap.values());
            boolean hasNull = true;
            do {
                hasNull = tempList.remove(null);
            } while (hasNull);

            // 默认有一个代表本对象类型的class成员变量
            if (tempList.size() > 1) {
                poObj = poClass.newInstance();
                final BeanMap poMap = new BeanMap(poObj);
                Object value = null;
                String keyName = null;
                for (final Object key : voMap.keySet()) {
                	if(key.equals("citys")) {
                		continue;
                	}
                    value = voMap.get(key);
                    keyName = key.toString();
                    if (value != null
                            && !value.getClass().isAssignableFrom(Class.class)
                            && !"".equals(value)) {
                        poMap.getWriteMethod(keyName).invoke(poObj,
                                castVoParam(value, poMap.getType(keyName)));
                    }
                }
            }
        } catch (final Exception e1) {
            e1.printStackTrace();
        }
        return poObj;
    }

    /**
     * 持久层对象转换为值对象.
     * 
     * @param po
     *            数据持久层对象.
     * @param <T>
     *            数据持久层对象类型.
     * 
     * @author 侯骏雄
     * @since 6.0.0
     */
    public <T> void poToVo(final T po) {
        try {
            final BeanMap voMap = new BeanMap(this);
            final BeanMap poMap = new BeanMap(po);
            Object value = null;
            String keyName = null;
            for (final Object key : poMap.keySet()) {
                value = poMap.get(key);
                keyName = key.toString();
                if (value != null
                        && !value.getClass().isAssignableFrom(Class.class)) {
                    voMap.getWriteMethod(keyName).invoke(this,
                            castPoParam(value));
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
