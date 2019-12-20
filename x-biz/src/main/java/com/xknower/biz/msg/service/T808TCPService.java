package com.xknower.biz.msg.service;

import com.xknower.biz.msg.module.conn.t808.T808ProtocolChannelHandler;
import com.xknower.biz.msg.module.conn.t808.T808ProtocolDecoder;
import com.xknower.thrid.netty.module.ProtocolSocketChannelInitializer;
import com.xknower.thrid.netty.service.NettyTCPServer;

/**
 * @author xknower
 */
public class T808TCPService {

    private final NettyTCPServer tcpNettyServer;

    public T808TCPService(T808ConnectionManager t808ConnectionService, T808MsgService t808MsgService) {
        //
        ProtocolSocketChannelInitializer ch =
                new ProtocolSocketChannelInitializer(
                        T808ProtocolChannelHandler.class, T808ProtocolDecoder.class, t808ConnectionService);
        //
        tcpNettyServer = new NettyTCPServer(ch, 8888);
    }
}
