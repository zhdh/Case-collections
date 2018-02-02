package com.data.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 */
public class ServerAcceptorHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger = LoggerFactory.getLogger(ServerAcceptorHandler.class);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        logger.info("Client: " + incoming.remoteAddress() + " join ");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        logger.info("Client: " + incoming.remoteAddress() + " disconnection");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel incoming = ctx.channel();
        logger.info("Client: " + incoming.remoteAddress() + " =====> " + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        logger.info("Client: " + incoming.remoteAddress() + " online");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        logger.info("Client: " + incoming.remoteAddress() + " leave");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        logger.warn("Client：" + incoming.remoteAddress().toString() + " abnormal close");
        incoming.close();
        cause.printStackTrace();
    }
}
