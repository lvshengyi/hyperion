package core.hades.dispatch;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author LvShengyI
 */
@AllArgsConstructor
@Getter
public class Handler {

    /**
     * 对应的controller
     */
    private Class controllerClass;

    /**
     * 对应的请求方法
     */
    private Method actionMethod;
}
