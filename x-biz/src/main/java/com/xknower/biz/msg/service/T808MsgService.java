package com.xknower.biz.msg.service;

import com.xknower.biz.msg.module.T808Msg;
import com.xknower.biz.msg.module.queue.T808MsgConsumeFactory;
import com.xknower.queue.module.IMsgQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 808 协议服务
 *
 * @author xknower
 */
@Service
public class T808MsgService {

    @Autowired
    private T808MsgConsumeFactory t808MsgConsumeFactory;

    /**
     * 压入消息到队列
     *
     * @param msg 808协议消息
     */
    public void send(T808Msg msg) {
        //
        IMsgQueue iMsgQueue = this.t808MsgConsumeFactory.send(msg);
        //
        System.out.println(String.format("消息 [%s %s %s] => 压入队列 [%s] ", msg.getOnlineNo(), msg.hash(), msg.type(), iMsgQueue.name()));
    }
}
