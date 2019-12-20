package com.xknower.thrid.netty.module;

import com.xknower.thrid.netty.module.decoder.ProtocolDecoder;
import com.xknower.thrid.netty.service.AbsConnectionManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 消息业务处理链, 初始化
 *
 * @author xknower
 */
public class ProtocolSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * M ITMsg 对象消息, 业务处理派发器
     */
    private final Class<? extends ProtocolChannelHandler> chClz;
    /**
     * M ITMsg 对象消息, 消息解码器
     */
    private final Class<? extends ProtocolDecoder> pdClz;
    /**
     * M ITMsg 对象消息的连接管理器
     */
    private AbsConnectionManager connectionManager;

    public ProtocolSocketChannelInitializer(
            Class<? extends ProtocolChannelHandler> chClz, Class<? extends ProtocolDecoder> pdClz, AbsConnectionManager connectionManager) {
        this.chClz = chClz;
        this.pdClz = pdClz;
        this.connectionManager = connectionManager;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 超过20分钟未收到客户端消息则自动断开客户端连接
        socketChannel.pipeline().addLast("idleStateHandler",
                new IdleStateHandler(20, 0, 0, TimeUnit.MINUTES));
        // 消息解码器
        socketChannel.pipeline().addLast("decoder", pdClz.newInstance());
        // 消息编码器
        socketChannel.pipeline().addLast("encoder", new ByteArrayEncoder());
        //
        ProtocolChannelHandler ch = chClz.newInstance();
        // 注入对象消息类型, 相关联的连接管理器
        ch.init(this.connectionManager);
        //
        socketChannel.pipeline().addLast(ch);
    }
}
