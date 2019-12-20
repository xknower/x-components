package com.xknower.thrid.netty.service.impl;

import com.xknower.thrid.netty.module.ITMsg;
import com.xknower.thrid.netty.service.AbsConnectionManager;
import com.xknower.thrid.netty.service.IConnectionService;

/**
 * 消息<TMsg> 连接管理器
 *
 * @author xknower
 */
public class DefaultConnectionManager extends AbsConnectionManager {

    public DefaultConnectionManager(IConnectionService iConnectionService) {
        super(iConnectionService);
    }

    @Override
    public Class<? extends ITMsg> support() {
        return TMsg.class;
    }

    @Override
    public void handle(ITMsg msg) {

    }
}
