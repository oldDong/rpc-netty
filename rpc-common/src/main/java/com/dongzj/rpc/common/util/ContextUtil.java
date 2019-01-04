package com.dongzj.rpc.common.util;

import com.dongzj.rpc.common.protocal.ProtocalConst;
import com.dongzj.rpc.common.serializer.HessianUtil;

import java.io.IOException;
import java.util.Arrays;

/**
 * 消息传输工具类
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 10:36
 */
public class ContextUtil {


    /**
     * 从数据中取出sessionID
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static Integer getSessionID(byte[] data) throws IOException {
        byte[] sessionData = Arrays.copyOfRange(data, 0, ProtocalConst.SESSION_ID_LENGTH);
        return (Integer) HessianUtil.deserialize(sessionData, null);
    }

    /**
     * 将sessionID和数据合并
     *
     * @param sessionId
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] mergeSessionID(Integer sessionId, byte[] data) throws IOException {
        byte[] newArr = new byte[data.length + ProtocalConst.SESSION_ID_LENGTH];
        System.arraycopy(HessianUtil.serialize(sessionId), 0, newArr, 0, ProtocalConst.SESSION_ID_LENGTH);
        System.arraycopy(data, 0, newArr, ProtocalConst.SESSION_ID_LENGTH, data.length);
        return newArr;
    }

    /**
     * 获取传输真实的消息体
     *
     * @param data
     * @return
     */
    public static byte[] getBody(byte[] data) {
        byte[] newArr = new byte[data.length - ProtocalConst.SESSION_ID_LENGTH];
        System.arraycopy(data, ProtocalConst.SESSION_ID_LENGTH, newArr, 0, data.length - ProtocalConst.SESSION_ID_LENGTH);
        return newArr;
    }
}
