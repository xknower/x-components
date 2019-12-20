package com.xknower.biz.msg.module.conn.t808;

import com.xknower.biz.msg.module.T808Msg;
import com.xknower.thrid.netty.module.ProtocolChannelHandler;

/**
 * 808 协议, 消息业务处理
 *
 * @author xknower
 */
public class T808ProtocolChannelHandler extends ProtocolChannelHandler<T808Msg> {

    @Override
    protected void handle(T808Msg msg) {
        // T808Msg 消息业务处理派发
        System.out.println(String.format("处理派发 [T808Msg] => %s | %s -> %s", msg.hash(), msg.type(), msg.getHexData()));
        //
        this.connectionManager.handle(msg);
    }
}
