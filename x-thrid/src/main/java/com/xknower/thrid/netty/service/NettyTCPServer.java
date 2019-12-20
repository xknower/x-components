package com.xknower.thrid.netty.service;

import com.xknower.thrid.netty.module.ProtocolSocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * TCP 连接服务
 *
 * @author xknower
 */
public class NettyTCPServer {

    /**
     * 消息业务处理链初始化服务
     */
    private ProtocolSocketChannelInitializer protocolSocketChannelInitializer;
    /**
     * 监听端口
     */
    private int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;
    private ServerBootstrap serverBootstrap;

    public NettyTCPServer(ProtocolSocketChannelInitializer protocolSocketChannelInitializer, int listenPort) {
        //
        this.protocolSocketChannelInitializer = protocolSocketChannelInitializer;
        this.port = listenPort;

        // 开启服务
        start();
    }

    public boolean start() {
        // (1)
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        //
        try {
            // (2)
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    // (3)
                    .channel(NioServerSocketChannel.class)
                    // (4)
                    .childHandler(protocolSocketChannelInitializer)
                    //
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, false);

            // 绑定端口，开始接收进来的连接
            System.out.println(String.format("Netty TCP [%s], Starting ...  ", this.port));
            channelFuture = serverBootstrap
                    .bind(port)
                    .sync();
            //
            return true;
        } catch (Exception ex) {
            System.out.println(String.format("Netty TCP [%s], Started Error ", this.port));
        }
        //
        return false;
    }

    /**
     * destroy
     *
     * @throws Exception
     */
    public void stop() throws Exception {
        this.closeChannel();
        this.closeEventLoop();
    }

    private void closeChannel() {
        try {
            channelFuture.channel().close();
        } catch (Exception ex) {
        } finally {
        }
    }

    private void closeEventLoop() {
        try {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        } catch (Exception ex) {
        }
    }
}
