package com.xknower.biz.msg.service;

import com.xknower.biz.msg.module.conn.t808.T808ProtocolChannelHandler;
import com.xknower.biz.msg.module.conn.t808.T808ProtocolDecoder;
import com.xknower.thrid.netty.module.ProtocolSocketChannelInitializer;
import com.xknower.thrid.netty.service.NettyTCPServer;
import org.springframework.stereotype.Component;

/**
 * @author xknower
 */
@Component
public class T808TCPService {

    private final NettyTCPServer tcpNettyServer;

    public T808TCPService(T808MsgService t808MsgService) {
        T808ConnectionService t808ConnectionService = new T808ConnectionService();
        T808ConnectionManager manager = new T808ConnectionManager(t808ConnectionService, t808MsgService);

        //
        ProtocolSocketChannelInitializer ch =
                new ProtocolSocketChannelInitializer(
                        T808ProtocolChannelHandler.class, T808ProtocolDecoder.class, manager);
        //
        tcpNettyServer = new NettyTCPServer(ch, 8888);
    }
}
