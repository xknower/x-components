package com.xknower.queue.service.impl;

import com.xknower.queue.module.IMsgHash;

import java.io.Serializable;

/**
 * 消息槽计算方式 (消息唯一标识符Hash值, 整除消息槽数, 将消息划分到不同槽)
 *
 * @author xknower
 */
public class DefaultMsgHash implements IMsgHash {

    /**
     * 消息槽
     */
    private int slot;

    public DefaultMsgHash(int slot) {
        this.slot = slot;
    }

    /**
     * 根据消息 标识, 计算消息对应槽
     *
     * @param serializable
     * @return
     */
    @Override
    public Serializable hash(Serializable serializable) {
        return Math.abs(serializable.hashCode()) % slot;
    }
}
