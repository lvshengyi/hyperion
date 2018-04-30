package base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author LvShengyI
 */
public class BaseTest {
    public static void print(Object obj) {
        System.out.println(JSON.toJSONString(obj, SerializerFeature.PrettyFormat));
    }
}
