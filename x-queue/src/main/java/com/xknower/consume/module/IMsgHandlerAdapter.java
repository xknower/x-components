package com.xknower.consume.module;

import com.xknower.queue.module.IMsg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息处理器适配器 (一个消息只能被一个处理器处理) <支持IMsg不同消息实现, 及不同消息和其不同的类型的处理适配>
 *
 * @author xknower
 */
public abstract class IMsgHandlerAdapter implements IMsgAdapter {

    /**
     * Class : 消息定义, 不同的IMsg实现消息基本类型
     * <p>
     * <消息类型 , 消息类型相应的处理 Handler>
     */
    private Map<Class, Map<Serializable, IMsgHandler>> msgHandlerMap = new HashMap<>();

    /**
     * 定义消息类型默认处理器
     */
    private Map<Class, IMsgHandler> defaultMsgHandlerMap = new HashMap<>();

    /**
     * 消息处理适配 (根据消息类, 获取消息处理映射器; 根据消息类型, 获取消息处理 Handler 并执行相应处理逻辑)
     *
     * @param msg 消息
     */
    @Override
    public void execute(IMsg msg) {
        Map<Serializable, IMsgHandler> msgHandlerMap = this.msgHandlerMap.get(msg.getClass());
        if (msgHandlerMap == null) {
            // 没有获取到相应消息类对应的消息处理映射器
            // msg.getClass(), msg.type()
            return;
        }

        //
        IMsgHandler msgHandler = msgHandlerMap.get(msg.type());
        if (msgHandler == null) {
            // 没有获取相应消息类型的处理 Handler
            // [获取默认处理器]
            msgHandler = defaultMsgHandlerMap.get(msg.getClass());
            if (msgHandler == null) {
                return;
            }
        }

        msgHandler.execute(msg);
    }

    /**
     * 初始化相关处理
     *
     * @param msgClass               消息类 (Msg.class)
     * @param msgHandlerMaps         消息处理器映射器, Handler 实现基础类
     * @param defaultMsgHandlerClass 消息类型默认处理 Handler实现 (DefaultMsgHandler.class)
     */
    protected void init(Class msgClass, Map<Serializable, IMsgHandler> msgHandlerMaps, IMsgHandler<? extends IMsgHandler> defaultMsgHandlerClass) {
        msgHandlerMap.put(msgClass, msgHandlerMaps);

        if (defaultMsgHandlerClass != null) {
            // 初始化默认处理器
            defaultMsgHandlerMap.put(msgClass, defaultMsgHandlerClass);
        }
    }
}
