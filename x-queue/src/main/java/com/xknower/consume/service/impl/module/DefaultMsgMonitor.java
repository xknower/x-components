package com.xknower.consume.service.impl.module;

import com.xknower.consume.module.IMsgMonitor;
import com.xknower.queue.module.IMsg;

/**
 * 消息监控处理器, 默认实现
 *
 * @author xknower
 */
public class DefaultMsgMonitor implements IMsgMonitor {

    @Override
    public void execute(IMsg msg) {
        System.out.println(String.format("Monitor : [%s , %s] => %s", msg.hash(), msg.type(), msg));
    }
}
