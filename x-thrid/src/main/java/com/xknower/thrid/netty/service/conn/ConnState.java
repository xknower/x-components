package com.xknower.thrid.netty.service.conn;

/**
 * 连接状态定义
 *
 * @author xknower
 */
public enum ConnState {

    // TCP 连接激活 (首次连接)
    ACTIVE,
    // TCP 连接维持 (再次上行消息)
    KEEP,
    // TCP连接断开  (终端主动断开)
    INACTIVE,
    // 连接超时 (服务端主动断开)
    TIMEOUT;

    ConnState() {
    }
}
