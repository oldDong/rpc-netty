package com.dongzj.rpc.common.entity;

import java.io.Serializable;

/**
 * RPC调用返回结果
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 10:28
 */
public class Response implements Serializable {

    private static final long serialVersionUID = -4468344537653077226L;

    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
