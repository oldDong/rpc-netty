package com.dongzj.rpc.core;

import com.dongzj.rpc.common.entity.Request;
import com.dongzj.rpc.common.serializer.HessianUtil;
import com.dongzj.rpc.entity.Global;

import java.io.IOException;

/**
 * 请求处理
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 13:53
 */
public class RequestHandler {

    public static byte[] handler(byte[] requestBytes) throws IOException {
        Request request = (Request) HessianUtil.deserialize(requestBytes, Global.getInstance().getClassLoader());

        Object object = ServiceInvoke.invoke(request);
        byte[] response = HessianUtil.serialize(object);
        return response;
    }
}
