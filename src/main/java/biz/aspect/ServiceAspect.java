package biz.aspect;

import core.zeus.annotation.Aspect;
import core.hades.annotation.Service;
import core.zeus.aop.AspectProxy;

import java.lang.reflect.Method;

/**
 * @author LvShengyI
 */
@Aspect(Service.class)
public class ServiceAspect extends AspectProxy {
    @Override
    public void before(Class clz, Method method, Object[] params) throws Throwable {
        System.out.println("前置方法");
        System.out.println("方法名:" + method.getName());
    }

    @Override
    public void after(Class clz, Method method, Object[] params, Object result) throws Throwable {
        System.out.println("后置方法");
        System.out.println("方法名:" + method.getName());
    }
}
