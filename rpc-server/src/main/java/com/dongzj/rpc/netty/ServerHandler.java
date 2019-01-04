package com.dongzj.rpc.netty;

import com.dongzj.rpc.common.util.CompressUtil;
import com.dongzj.rpc.common.util.ContextUtil;
import com.dongzj.rpc.core.RequestHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty服务端收发数据
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 14:20
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] bytes = (byte[]) msg;
        logger.debug("接受大小： " + bytes.length);

        Integer sessionID = ContextUtil.getSessionID(bytes);
        logger.debug("接受sessionID:{}", sessionID);
        byte[] bytesrc = CompressUtil.uncompress(ContextUtil.getBody(bytes));

        byte[] responseBytes = ContextUtil.mergeSessionID(sessionID, CompressUtil.compress(RequestHandler.handler(bytesrc)));
        logger.debug("服务端返回大小：" + responseBytes.length);

        ctx.writeAndFlush(responseBytes);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
