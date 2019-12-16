package com.xknower.queue.service;

import com.xknower.queue.module.IMsgQueue;
import com.xknower.queue.module.IMsgQueueFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * (保证同一个车辆的数据在同一个队列被同一个线程消费)
 *
 * @author xknower
 */
public abstract class AbsMsgQueueFactory implements IMsgQueueFactory {

    /**
     * 消息队列 (按组分不同的队列) | 消费/队列 组, 消费/队列 槽
     * <p>
     * 消息队列实现类 : ConcurrentHashMap
     */
    protected Map<Serializable, IMsgQueue> groupQueueList = new ConcurrentHashMap<Serializable, IMsgQueue>();

    /**
     * 消息发往指定槽
     *
     * @param msg
     * @param specified 消息槽 (由消息唯一标识符计算)
     * @param <T>
     * @return
     */
    @Override
    public <T> IMsgQueue<T> sendSpecified(T msg, Serializable specified) {
        IMsgQueue<T> build = groupQueueList.get(specified);
        if (build == null) {
            // 没有消息队列
            return null;
        }
        // 消息队列
        build.put(msg);

        //
        return build;
    }
}
