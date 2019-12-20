package com.xknower.thrid.netty.module;

import com.xknower.thrid.netty.service.AbsConnectionManager;
import com.xknower.thrid.netty.service.conn.ConnState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 消息业务派发处理器 (@Sharable)
 * <p>
 * 基于 <SimpleChannelInboundHandler>
 *
 * @author xknower
 */
public abstract class ProtocolChannelHandler<M extends ITMsg> extends SimpleChannelInboundHandler<M> {

    /**
     * M 消息对应的连接管理器
     */
    protected AbsConnectionManager<M> connectionManager;

    /**
     * 初始化, 注入对应消息类型的连接管理器
     *
     * @param connectionManager
     */
    protected void init(AbsConnectionManager<M> connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * 读取数据 ， 客户端向服务端发来数据，每次都会回调此方法，表示有数据可读
     * <p>
     * * SimpleChannelInboundHandler的channelRead0还有一个好处就是你不用关心释放资源，因为源码中已经帮你释放了，所以如果你保存获取的信息的引用，是无效的  * -> ProtocolDecoder 重新创建808消息对象
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, M msg) throws Exception {
        // 消息业务处理
        handle(msg);

        // 更新连接
        this.connectionManager.connection(msg.hash(), channelHandlerContext.channel());
    }

    /**
     * M 消息业务处理
     *
     * @param msg
     */
    protected abstract void handle(M msg);

    /**
     * 连接关闭 (终端主动断开或者服务端终端断开)
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String.format("<== 连接关闭 [%s -> %s]", ctx.channel().localAddress(), ctx.channel().remoteAddress());

        connectionManager.disconnection(ctx, ConnState.INACTIVE);
    }

    /**
     * TCP 连接超时, 主动关联连接
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 事件回调
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                // 读超时 => 关闭连接
                ctx.channel().close();
                //
                connectionManager.disconnection(ctx, ConnState.TIMEOUT);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
