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
        System.out.println(method.getName() + "前置方法");
        System.out.print("参数：");
        for (Object param : params) {
            System.out.print(param);
        }
        System.out.println();
    }

    @Override
    public void after(Class clz, Method method, Object[] params, Object result) throws Throwable {
        System.out.println(method.getName() + "后置方法");
        System.out.print("参数：");
        for (Object param : params) {
            System.out.print(param);
        }
        System.out.println();
        System.out.println("返回的结果是：" + result);
    }
}
