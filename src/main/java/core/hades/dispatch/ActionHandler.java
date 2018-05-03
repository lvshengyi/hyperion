package core.hades.dispatch;

import core.hades.annotation.Action;
import core.zeus.bean.BeanContainer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author LvShengyI
 */
public class ActionHandler {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>(16);

    static {
        Set<Class> controllerClassSet = BeanContainer.getClassSet(Action.class);

        for (Class clz : controllerClassSet) {
            Method[] methods = clz.getMethods();

            for (Method method : methods) {
                if(method.isAnnotationPresent(Action.class)){
                    Action action = method.getAnnotation(Action.class);
                    String mapping  = action.value();
                    String[] array = mapping.split(":");
                    String requestMethod = array[0];
                    String requestPath = array[1];

                    Request request = new Request(requestMethod, requestPath);
                    Handler handler = new Handler(clz, method);

                    ACTION_MAP.put(request, handler);
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath){
        Request request = new Request(requestMethod, requestPath);

        return ACTION_MAP.get(request);
    }
}
