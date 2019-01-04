package com.dongzj.rpc.entity;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端全局参数
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 11:43
 */
public class Global {

    private Global() {
        methodCache = new ConcurrentHashMap<String, Method>();
    }

    /**
     * 静态内部类实现的单例模式
     */
    private static class SingleHolder {
        private static final Global INSTANCE = new Global();
    }

    /**
     * 单例
     *
     * @return
     */
    public static Global getInstance() {
        return SingleHolder.INSTANCE;
    }

    /**
     * netty接受缓冲区大小
     */
    private Integer MaxBuf = 1024;

    /**
     * 服务应用名称
     */
    private String serviceName;

    /**
     * 网络连接配置信息
     */
    private String ip;
    private Integer port;
    private Integer timeout;

    /**
     * 服务缓存
     */
    private Map<String, Object> serviceImpl;

    /**
     * 服务实现类缓存
     */
    private Map<String, Class> serviceClass;

    private Map<String, Method> methodCache;

    private ClassLoader classLoader;

    public Integer getMaxBuf() {
        return MaxBuf;
    }

    public void setMaxBuf(Integer maxBuf) {
        MaxBuf = maxBuf;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Object getServiceImpl(String serviceName) {
        return serviceImpl.get(serviceName);
    }

    public void setServiceImpl(Map<String, Object> serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public Class getServiceClass(String serviceImplName) {
        return serviceClass.get(serviceImplName);
    }

    public void setServiceClass(Map<String, Class> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Method getMethod(String serviceName, String methodName, List<String> paramsTypesName) {
        return this.methodCache.get(buildKey(serviceName, methodName, paramsTypesName));
    }

    public void putMethod(String serviceName, String methodName, List<String> paramsTypesName, Method method) {
        this.methodCache.put(buildKey(serviceName, methodName, paramsTypesName), method);
    }

    private String buildKey(String serviceName, String methodName, List<String> paramsTypeName) {
        StringBuilder methodKey = new StringBuilder(serviceName);
        methodKey.append("-").append(methodName);
        for (String s : paramsTypeName) {
            methodKey.append("-").append(s);
        }
        return methodKey.toString();
    }
}
