package com.xknower.biz.msg.module;

import com.xknower.queue.module.IMsg;

import java.io.Serializable;

/**
 * 车联网 808 协议, 实体类定义
 *
 * @author xknower
 */
public class T808Msg implements IMsg {

    /**
     * 消息报文头
     */
    private MsgHeader header = new MsgHeader();
    /**
     * 消息体
     */
    private IMsgBody IMsgBody;

    /**
     * 原始数据
     */
    private byte[] data;

    public T808Msg() {

    }

    public T808Msg(String onlineNo, int msgType, byte[] bytes) {
        header = new MsgHeader();
        header.setOnlineNo(onlineNo);
        header.setMsgType(msgType);
        this.data = bytes;
    }

    public String getOnlineNo() {
        return header == null ? "" : header.getOnlineNo();
    }

    /**
     * 序列化, 编码对象为字节流
     *
     * @return
     */
    public final byte[] serialization() {
        return null;
    }

    /**
     * 反序列化, 解码字节流为对象
     *
     * @param bytes 对象字节流
     */
    public final void deserialization(byte[] bytes) {

    }

    @Override
    public Serializable hash() {
        return getOnlineNo();
    }

    @Override
    public Serializable type() {
        return header != null ? header.getMsgType() : 0;
    }
}
