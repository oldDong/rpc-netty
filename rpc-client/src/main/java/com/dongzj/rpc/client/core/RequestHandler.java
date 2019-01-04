package com.dongzj.rpc.client.core;

import com.dongzj.rpc.client.RPC;
import com.dongzj.rpc.client.entity.Address;
import com.dongzj.rpc.client.net.TCPClient;
import com.dongzj.rpc.common.entity.Request;
import com.dongzj.rpc.common.serializer.HessianUtil;
import com.dongzj.rpc.common.util.CompressUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 选择服务，进行TCP请求
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 17:02
 */
public class RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static Map<Address, TCPClient> tcpClientCache = new ConcurrentHashMap<>();

    private static Object lockHelper = new Object();

    public static Object request(String serviceName, Request request, Class returnType) throws Exception {

        Address addr = LoadBalance.loadbalanceRandom(serviceName);
        byte[] requestBytes = CompressUtil.compress(HessianUtil.serialize(request));

        TCPClient tcpClient = getTCPClient(addr, RPC.getService(serviceName).getTimeout());

        logger.debug("客户端发送数据：{}", requestBytes);
        Integer sessionID = tcpClient.sendMsg(requestBytes);
        if (Objects.isNull(sessionID)) {
            throw new Exception("send data error!");
        }

        byte[] responseBytes = tcpClient.getData(sessionID);
        return HessianUtil.deserialize(CompressUtil.uncompress(responseBytes), null);

    }

    private static TCPClient getTCPClient(Address address, int timeout) {
        TCPClient tcpClient = tcpClientCache.get(address);
        if (Objects.isNull(tcpClient)) {
            synchronized (lockHelper) {
                tcpClient = tcpClientCache.get(address);
                if (Objects.isNull(tcpClient)) {
                    tcpClient = new TCPClient(address.getHost(), address.getPort(), timeout);
                    tcpClientCache.put(address, tcpClient);
                }
            }
        }
        return tcpClient;
    }

}
