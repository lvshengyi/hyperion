package core.zeus.aop;

/**
 * @author LvShengyI
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
