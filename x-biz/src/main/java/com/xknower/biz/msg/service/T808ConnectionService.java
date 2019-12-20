package com.xknower.biz.msg.service;

import com.xknower.thrid.netty.service.IConnectionService;
import com.xknower.thrid.netty.service.conn.ConnState;
import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * @author xknower
 */
public class T808ConnectionService implements IConnectionService {

    @Override
    public void connection(Serializable key, Channel channel, ConnState state) {
        // 消息连接处理
        System.out.println(String.format("消息连接处理 [%s] => %s", key, state));
    }
}
