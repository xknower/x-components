package com.xknower.consume.module;

import com.xknower.queue.module.IMsg;
import com.xknower.queue.module.IMsgQueue;

import java.util.List;

/**
 * 消息消费者
 *
 * @author xknower
 */
public class MsgConsume extends Thread {

    private boolean start = true;

    /**
     * 消息队列
     */
    private final IMsgQueue<IMsg> iMsgQueue;

    /**
     * 消息适配器 <消息处理器适配器, 消息监控处理器适配器>
     */
    private final IMsgAdapter msgHandler;

    public MsgConsume(Integer index, IMsgQueue msgQueue, IMsgAdapter msgHandler) {
        this.iMsgQueue = msgQueue;
        this.msgHandler = msgHandler;
        this.setDaemon(true);
        this.setName("消费线程 [" + index + "][" + msgQueue.name() + "]");
    }

    @Override
    public void run() {
        while (start) {
            List<IMsg> msgList;
            try {
                msgList = iMsgQueue.pullList();
                if (msgList == null) {
                    continue;
                }

                //
                for (IMsg msg : msgList) {
                    msgHandler.execute(msg);
                    // String.format("==> 消息处理完成 ==> [ %s ][ %s ]", msg.hash(), msg.type()
                }
            } catch (Exception e) {
                // 消息业务出错 ==> [ %s %s ]
                // 处理消息出错
            }
        }
    }

    public void stop(boolean flag) {
        // 停止消费
        start = flag;
    }
}
