package com.data.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 */
public class ServerAcceptorStartUp {

    private Logger logger = LoggerFactory.getLogger(ServerAcceptorStartUp.class);

    private int port;

    private ServerAcceptorStartUp(int port){
        this.port = port;
    }

    public void run() throws Exception{
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ServerAcceptorInitalizer()).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            logger.info("Server started -> port : " + port);
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
            logger.info("Server closed");
        }
    }

    public static void main(String[] args) throws Exception {
        new ServerAcceptorStartUp(6060).run();
    }


}
