package com.xknower.thrid.netty.module;

import java.io.Serializable;

/**
 * Msg 消息定义 <Netty TCP>
 *
 * @author xknower
 */
public interface ITMsg {

    /**
     * 消息 Hash (消息唯一标识符)
     *
     * @return Hash 值
     */
    Serializable hash();
}
