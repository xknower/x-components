package com.xknower.thrid.netty.service;

import com.xknower.thrid.netty.service.conn.ConnState;
import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * 连接管理业务相关服务
 *
 * @author xknower
 */
public interface IConnectionService {

    /**
     * 终端 TCP 连接 (首次上行消息, 未缓存关联)
     *
     * @param key     终端唯一标识符
     * @param channel 会话
     * @param state   连接状态 [首次连接上行消息; 再次上行消息; 终端注定断开; 终端连接超时断开]
     */
    void connection(Serializable key, Channel channel, ConnState state);
}
