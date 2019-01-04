package com.dongzj.rpc.client.proxy;

/**
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 19:18
 */
public class ProxyFactory {

    public static <T> T create(Class<?> type, String serviceName, String serviceImplName) {
        ProxyHandler handler = new ProxyHandler(serviceName, serviceImplName);
        return (T) handler.bind(new Class<?>[]{type});
    }
}
