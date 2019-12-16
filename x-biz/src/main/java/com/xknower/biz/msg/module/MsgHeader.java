package com.xknower.biz.msg.module;

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
