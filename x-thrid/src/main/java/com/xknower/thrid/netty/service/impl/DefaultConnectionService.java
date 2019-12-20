package com.xknower.thrid.netty.service.impl;

import com.xknower.thrid.netty.service.IConnectionService;
import com.xknower.thrid.netty.service.conn.ConnState;
import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * 连接管理业务服务默认实现
 *
 * @author xknower
 */
public class DefaultConnectionService implements IConnectionService {

    @Override
    public void connection(Serializable key, Channel channel, ConnState state) {
        
    }
}
