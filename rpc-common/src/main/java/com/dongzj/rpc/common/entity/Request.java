package com.dongzj.rpc.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * RPC调用请求
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 10:23
 */
@Data
public class Request implements Serializable {

    private static final long serialVersionUID = -2859090193935434258L;

    /**
     * 服务实现类名字
     */
    private String serviceImplName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 调动方法参数的Class路径
     */
    private List<String> paramsTypesName;

    /**
     * 调用方法参数的实例，顺序与上面的Class保持一致
     */
    private List<Object> paramsValues;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
