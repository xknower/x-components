package com.xknower.biz.msg.module;

import com.xknower.biz.msg.utils.MyBuffer;

/**
 * 808 协议, 消息头定义
 *
 * @author xknower
 */
public class MsgHeader {

    /**
     * 上线号 (消息唯一标识符)
     */
    private String onlineNo;

    /**
     * 消息类型 [2B , 无符号16位]
     */
    private int msgType;
    /**
     * 流水号
     */
    private short serialNo;

    /**
     * 消息体属性
     */
    private short messageBodyProperty;

    /**
     * 总包数
     */
    private short totalPacketCount;
    /**
     * 分包号
     */
    private short packetNo;

    /**
     * 序列化, 编码对象为字节流
     *
     * @return
     */
    public final byte[] serialization() {
        return new byte[0];
    }

    /**
     * 反序列化, 解码字节流为对象
     *
     * @param bytes 对象字节流
     */
    public final void deserialization(byte[] bytes) {
        //
        MyBuffer buff = new MyBuffer(bytes);
        msgType = (buff.getShort() & 0xffff);
        messageBodyProperty = buff.getShort();
        onlineNo = buff.getFormatString(6, "%02X");
        serialNo = buff.getShort();
        if (isPackage()) {
            // 分包处理
            totalPacketCount = buff.getShort();
            packetNo = buff.getShort();
        }
    }

    /**
     * 消息体长度
     */
    public final int getMsgSize() {
        return messageBodyProperty & 0x03FF;
    }

    /**
     * 分包发送
     */
    public final boolean isPackage() {
        return (messageBodyProperty & 0x2000) == 0x2000;
    }

    /**
     * 判断是否加密
     *
     * @return true 消息体经过RSA加密
     */
    public final boolean isEncryption() {
        // 是否 RSA 算法加密
        return (messageBodyProperty & 0x0400) != 0;
    }

    public void setOnlineNo(String onlineNo) {
        this.onlineNo = onlineNo;
    }

    public String getOnlineNo() {
        return onlineNo == null ? "" : onlineNo;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgType() {
        return msgType;
    }
}
