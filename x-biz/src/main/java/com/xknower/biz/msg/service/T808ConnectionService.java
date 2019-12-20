package com.xknower.biz.msg.service;

import com.xknower.thrid.netty.service.IConnectionService;
import com.xknower.thrid.netty.service.conn.ConnState;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author xknower
 */
@Component
public class T808ConnectionService implements IConnectionService {

    @Override
    public void connection(Serializable key, Channel channel, ConnState state) {
        System.out.println(String.format("消息[%s]连接 => %s", key, state));
    }
}
