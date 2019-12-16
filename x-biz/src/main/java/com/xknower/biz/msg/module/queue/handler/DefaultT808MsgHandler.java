package com.xknower.biz.msg.module.queue.handler;

import com.xknower.biz.msg.module.queue.T808MsgHandler;
import com.xknower.queue.module.IMsg;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 默认808协议消息, 处理器
 *
 * @author xknower
 */
@Component
public class DefaultT808MsgHandler implements T808MsgHandler {

    @Override
    public Serializable support() {
        return null;
    }

    @Override
    public void execute(IMsg msg) {
        System.out.println(String.format("T808MsgHandler : [%s , %s] => %s", msg.hash(), msg.type(), msg));
    }
}
