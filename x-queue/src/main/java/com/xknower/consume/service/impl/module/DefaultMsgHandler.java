package com.xknower.consume.service.impl.module;

import com.xknower.consume.module.IMsgHandler;
import com.xknower.queue.module.IMsg;

import java.io.Serializable;

/**
 * 消息处理器, 默认处理
 *
 * @author xknower
 */
public class DefaultMsgHandler implements IMsgHandler {

    @Override
    public Serializable support() {
        // 消息类型
        return null;
    }

    @Override
    public void execute(IMsg msg) {
        //
        System.out.println(String.format("Handler : [%s , %s] => %s", msg.hash(), msg.type(), msg));
    }
}
