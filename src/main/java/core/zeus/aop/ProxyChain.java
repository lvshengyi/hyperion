package core.zeus.aop;

import lombok.Getter;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LvShengyI
 */
public class ProxyChain {

    @Getter
    private final Class targetClass;

    private final Object targetObject;

    @Getter
    private final Method targetMethod;

    private final MethodProxy methodProxy;

    @Getter
    private final Object[] methodParams;

    private List<Proxy> proxyList = new ArrayList<>();

    private Integer proxyIndex = 0;

    public ProxyChain(Class targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Object doProxyChain() throws Throwable{
        Object methodResult;

        if(proxyIndex < proxyList.size()){
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        }else{
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }

        return methodResult;
    }
}
