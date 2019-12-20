package com.xknower.biz.msg.service;

import com.xknower.biz.msg.module.T808Msg;
import com.xknower.biz.msg.module.queue.T808MsgConsumeFactory;
import com.xknower.consume.service.AbsMsgConsumeFactory;
import com.xknower.queue.module.IMsgQueue;

/**
 * 808 协议服务
 *
 * @author xknower
 */
public class T808MsgService {

    private AbsMsgConsumeFactory msgConsumeFactory;

    public T808MsgService(T808MsgConsumeFactory t808MsgConsumeFactory) {
        this.msgConsumeFactory = t808MsgConsumeFactory;
    }

    /**
     * 压入消息到队列
     *
     * @param msg 808协议消息
     */
    public void send(T808Msg msg) {
        //
        IMsgQueue iMsgQueue = this.msgConsumeFactory.send(msg);
        //
        System.out.println(String.format("消息 [%s %s %s] => 压入队列 [%s] ", msg.getOnlineNo(), msg.hash(), msg.type(), iMsgQueue.name()));
    }
}
