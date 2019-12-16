package com.xknower.consume.service;

import com.xknower.consume.module.IMsgAdapter;
import com.xknower.consume.module.IMsgHandlerAdapter;
import com.xknower.consume.module.MsgConsume;
import com.xknower.queue.module.IMsgQueue;
import com.xknower.queue.module.IMsgQueueFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 消息队列, 消费者工厂类
 *
 * @author xknower
 */
public abstract class AbsMsgConsumeFactory {

    /**
     * 消息队列工厂
     */
    private final IMsgQueueFactory msgQueueFactory;

    /**
     * 消息处理适配器
     */
    private final IMsgAdapter msgHandler;

    /**
     * 消费线程倍数 (多个消费线程, 消费同一个队列)
     * <p>
     * multiple = 1 , 同一个队列只能被一个消费者消费
     */
    private final int multiple = 1;

    /**
     * 消费者
     */
    private List<List<MsgConsume>> consumeList;

    /**
     * 初始化消费工厂
     *
     * @param msgQueueFactory 自定义消费工厂
     * @param msgHandler      消费适配器
     */
    public AbsMsgConsumeFactory(IMsgQueueFactory msgQueueFactory, IMsgHandlerAdapter msgHandler) {
        this.msgQueueFactory = msgQueueFactory;
        this.msgHandler = msgHandler;
    }

    /**
     * 创建消息队列及其消费者
     *
     * @param index 队列标识名称
     * @return
     */
    private List<MsgConsume> build(Integer index) {
        // 创建消费队列
        IMsgQueue msgQueue = msgQueueFactory.build(index);

        // 更具消费队列, 创建线程消费线程
        return IntStream.range(0, multiple)
                .mapToObj((i) -> {
                    // 初始化
                    MsgConsume consume = new MsgConsume(index * i, msgQueue, msgHandler);
                    // 开启
                    consume.start();
                    return consume;
                })
                .collect(Collectors.toList());
    }

    /**
     * 创建消费者 (afterPropertiesSet)
     */
    public void init() {
        // 创建消费者 (从 0开始 -> messageQueueCount)
        this.consumeList =
                IntStream
                        .range(0, this.msgQueueFactory.queueCount())
                        .mapToObj(this::build)
                        .collect(Collectors.toList());
    }

    /**
     * 关闭消费 (destroy)
     */
    public void stop() {
        // 关闭并销毁, 所有消费者
        consumeList.forEach(is -> is.forEach(i -> i.stop(true)));
    }
}
