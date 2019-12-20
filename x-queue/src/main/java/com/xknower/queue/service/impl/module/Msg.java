package com.xknower.queue.service.impl.module;

import com.xknower.queue.module.IMsg;

import java.io.Serializable;

/**
 * 消息定义 (默认消息对象)
 *
 * @author xknower
 */
public class Msg implements IMsg {

    /**
     * 消息号 (消息唯一标识符)
     */
    private String no;
    /**
     * 消息类型
     */
    private int type;

    @Override
    public Serializable hash() {
        return no;
    }

    @Override
    public Serializable type() {
        return type;
    }

    @Override
    public byte[] serialization() {
        return new byte[0];
    }

    @Override
    public void deserialization(byte[] bytes) {
    }
}
