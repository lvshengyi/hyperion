package common.utils;

/**
 * @author LvShengyI
 */
public class TimeUtil {

    /**
     * 获取当前时间戳，单位：毫秒
     * @return
     */
    public static Long getTimeStamp(){
        return System.currentTimeMillis();
    }

    /**
     * 将毫秒转换成秒
     *
     * @param mesc
     * @return
     */
    public static Long mescToSec(Long mesc){
        return mesc / 1000;
    }

    /**
     * 将秒转换成毫秒
     * @param sec
     * @return
     */
    public static Long secToMesc(Long sec) {
        return sec * 1000;
    }
}
