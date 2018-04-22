package common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author LvShengyI
 *
 * properties文件读取工具
 */
@Slf4j
public class PropUtil {

    /**
     * 配置
     */
    private static Properties prop;

    /**
     * 表示是否初始化（是否已经执行过ini（））
     */
    private static Boolean hasIni = false;

    /**
     * 默认文件名
     */
    private static final String DEFAULT_PROP_FILE_NAME = "DataSource.properties";

    /**
     * 初始化，读取properties文件
     *
     * @return
     */
    private static Properties ini() {
        try(InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_PROP_FILE_NAME)) {
            if (is == null) {
                throw new FileNotFoundException(DEFAULT_PROP_FILE_NAME + " is not find");
            }

            prop = new Properties();
            prop.load(is);
        } catch (Exception e) {
            log.error("load properties file failure", e);
        }

        hasIni = true;

        return prop;
    }

    public static String getString(String key) {
        if(!hasIni){
            ini();
        }

        final String DEFAULT_VALUE = "";

        return getString(key, DEFAULT_VALUE);
    }

    public static String getString(String key, String defaultValue) {
        if(!hasIni){
            ini();
        }

        if(prop.containsKey(key)){
            return prop.getProperty(key);
        }

        return defaultValue;
    }

    public static Integer getInteger(String key) {
        if(!hasIni){
            ini();
        }

        final Integer DEFAULT_VALUE = 0;

        return getInteger(key, DEFAULT_VALUE);
    }

    public static Integer getInteger(String key, Integer defaultValue) {
        if(!hasIni){
            ini();
        }

        if(prop.containsKey(key)){
            return CastUtil.castToInteger(prop.getProperty(key));
        }

        return defaultValue;
    }

    public static Long getLong(String key) {
        if(!hasIni){
            ini();
        }

        final Long DEFAULT_VALUE = 0L;

        return getLong(key, DEFAULT_VALUE);
    }

    public static Long getLong(String key, Long defaultValue) {
        if(!hasIni){
            ini();
        }

        if(prop.containsKey(key)){
            return CastUtil.castToLong(prop.getProperty(key));
        }

        return defaultValue;
    }

    public static Double getDouble(String key) {
        if(!hasIni){
            ini();
        }

        final Double DEFAULT_VALUE = 0d;

        return getDouble(key, DEFAULT_VALUE);
    }

    public static Double getDouble(String key, Double defaultValue) {
        if(!hasIni){
            ini();
        }

        if(prop.containsKey(key)){
            return CastUtil.castToDouble(prop.getProperty(key));
        }

        return defaultValue;
    }
}
