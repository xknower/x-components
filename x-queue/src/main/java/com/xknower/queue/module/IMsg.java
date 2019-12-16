package com.xknower.queue.module;

import java.io.Serializable;

/**
 * 消息实体定义
 *
 * @author xknower
 */
public interface IMsg {

    /**
     * 计算消息 Hash (根据消息唯一标识符)
     *
     * @return Hash 值
     */
    Serializable hash();

    /**
     * 消息类型 (不同的消息类型, 有不同的处理逻辑)
     *
     * @return
     */
    Serializable type();
}
