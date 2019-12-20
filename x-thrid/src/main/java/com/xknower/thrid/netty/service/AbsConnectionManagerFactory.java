package com.xknower.thrid.netty.service;

import com.xknower.thrid.netty.module.ITMsg;

import java.util.HashMap;
import java.util.Map;

/**
 * 连接管理工厂类 - 根据消息类型提供对应的连接管理器
 *
 * @author xknower
 */
public abstract class AbsConnectionManagerFactory {

    /**
     * 基于消息类型的连接管理实现
     */
    private Map<Class, AbsConnectionManager> connectionManagerMap = new HashMap<>();

    protected void init(Class<? extends ITMsg> msgClz, AbsConnectionManager connectionManager) {
        //
        connectionManagerMap.put(msgClz, connectionManager);
    }
}
