package core.zeus.aop;

import java.lang.reflect.Method;

/**
 * @author LvShengyI
 */
public abstract class AspectProxy implements Proxy {

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class clz = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(clz, method, params)) {
                before(clz, method, params);
                result = proxyChain.doProxyChain();
                after(clz, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            error(clz, method, params, e);

            throw e;
        } finally {
            end();
        }

        return result;
    }

    public void begin() {

    }

    public Boolean intercept(Class clz, Method method, Object[] params) throws Throwable {
        return true;
    }

    public abstract void before(Class clz, Method method, Object[] params) throws Throwable;

    public abstract void after(Class clz, Method method, Object[] params, Object result) throws Throwable;

    public void error(Class clz, Method method, Object[] params, Throwable e) {

    }

    public void end() {

    }
}
