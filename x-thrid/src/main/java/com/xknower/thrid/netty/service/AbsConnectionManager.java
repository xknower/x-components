package com.xknower.thrid.netty.service;

import com.xknower.thrid.netty.module.ITMsg;
import com.xknower.thrid.netty.service.conn.ConnState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接管理器 - 管理一种消息类型, 对应终端的所有连接
 * <p>
 * T 消息连接管理器
 *
 * @author xknower
 */
public abstract class AbsConnectionManager<M extends ITMsg> {

    private static final AttributeKey<Serializable> KEY = AttributeKey.valueOf("key");

    private IConnectionService connectionService;

    public AbsConnectionManager(IConnectionService connectionService) {
        //
        this.connectionService = connectionService;
    }

    /**
     * 连接信息 - <标识符, 终端连接对象>
     */
    private Map<Serializable, Channel> channelCache = new ConcurrentHashMap<>();

    /**
     * 连接管理器, 管理的消息类型
     *
     * @return 返回连接管理器对应消息类型
     */
    public abstract Class<M> support();

    /**
     * T 消息业务处理 (消息解析完成后, 业务派发 [发送消息到队列])
     */
    public abstract void handle(M msg);

    /**
     * 消息上行, 更新会话缓存及相关信息
     * <p>
     * 一个终端标识符, 映射一个TCP会话 (一个TCP会话, 不限制唯一终端标识符)
     *
     * @param key
     * @param channel
     */
    public void connection(Serializable key, Channel channel) {
        // 已关联
        if (channelCache.containsKey(key)) {
            connectionService.connection(key, channel, ConnState.KEEP);

            try {
                // 连接重置 (唯一标识符, 被多个终端连接使用) (一个TCP连接上行多个终端消息)
                String a = channel.localAddress().toString() + channel.remoteAddress().toString();
                String b = channelCache.get(key).localAddress().toString() + channelCache.get(key).remoteAddress().toString();
                if (!a.equalsIgnoreCase(b)) {
                    System.out.println(String.format("连接切换 -> %s | %s | %s", key, a, b));
                }
            } catch (Exception e) {
                System.out.println(String.format("Channel Error"));
            }
            return;
        }

        // 缓存连接, 并缓存唯一标识符
        channel.attr(KEY).set(key);
        channelCache.put(key, channel);

        // TCP 首次连接
        connectionService.connection(key, channel, ConnState.ACTIVE);
    }

    /**
     * 缓存会话中获取 TCP 连接, 唯一标识符
     *
     * @param channel 会话
     * @return
     */
    public Serializable channelKey(Channel channel) {
        return channel.attr(KEY).get();
    }

    /**
     * 根据唯一标识符, 获取响应会话
     *
     * @param key 会话唯一标识符
     */
    public Channel channel(String key) {
        return channelCache.get(key);
    }

    public void disconnection(ChannelHandlerContext ctx, ConnState state) {
        Serializable key = channelKey(ctx.channel());
        if (key != null) {
            // TCP 建立连接, 并在会话中已经缓存终端唯一标识符
            connectionService.connection(key, ctx.channel(), state);
        } else {
            // TCP 连接不稳定, 未缓存会话标识就断开连接 (闪连闪断)
        }

        System.out.println(String.format("连接[%s]断开 => %s", key, state));
    }
}
