package common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author LvShengyI
 */
public class CastUtil {

    public static String castToString(Object obj) {
        final String DEFAULT_VALUE = "";

        return castToString(obj, DEFAULT_VALUE);
    }

    public static String castToString(Object obj, String defaultValue) {
        return obj == null ? defaultValue : String.valueOf(obj);
    }

    public static Float castToFloat(Object obj) {
        Float DEFAULT_VALUE = 0f;

        return castToFloat(obj, DEFAULT_VALUE);
    }

    public static Float castToFloat(Object obj, Float defaultValue) {
        Float value = defaultValue;

        if (obj != null) {
            String strValue = castToString(obj);

            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    value = Float.parseFloat(strValue);
                } catch (Exception e) {
                    value = defaultValue;
                }
            }
        }

        return value;
    }

    public static Double castToDouble(Object obj) {
        Double DEFAULT_VALUE = 0d;

        return castToDouble(obj, DEFAULT_VALUE);
    }

    public static Double castToDouble(Object obj, Double defaultValue) {
        Double value = defaultValue;

        if (obj != null) {
            String strValue = castToString(obj);

            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    value = Double.parseDouble(strValue);
                } catch (Exception e) {
                    value = defaultValue;
                }
            }
        }

        return value;
    }

    public static Long castToLong(Object obj) {
        final Long DEFAULT_VALUE = 0L;

        return castToLong(obj, DEFAULT_VALUE);
    }

    public static Long castToLong(Object obj, Long defaultValue) {
        Long value = defaultValue;

        if (obj != null) {
            String strValue = String.valueOf(obj);

            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    value = Long.parseLong(strValue);
                } catch (Exception e) {
                    value = defaultValue;
                }
            }
        }

        return value;
    }

    public static Integer castToInteger(Object obj) {
        final Integer DEFAULT_VALUE = 0;

        return castToInteger(obj, DEFAULT_VALUE);
    }

    public static Integer castToInteger(Object obj, Integer defaultValue) {
        Integer value = defaultValue;

        if (obj != null) {
            String strValue = String.valueOf(obj);

            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    value = Integer.parseInt(strValue);
                } catch (Exception e) {
                    value = defaultValue;
                }
            }
        }

        return value;
    }
}
