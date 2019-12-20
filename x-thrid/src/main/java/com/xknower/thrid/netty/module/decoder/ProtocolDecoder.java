package com.xknower.thrid.netty.module.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 连接数据消息解码器, 将原始数据进行 粘包解码等操作解析成相应对象
 *
 * <p>
 * 基于字节解码器 <ByteToMessageDecoder>
 *
 * @author xknower
 */
public abstract class ProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        if (in == null) {
            return;
        }

        byte[] bytes = packetSplicing(in);
        if (bytes != null) {
            // 粘包解码完成
            if (!deserialization(bytes, list)) {
                // 返回 true, 继续粘包并解析
                return;
            }
        }
        // 粘包未完成, 等待数据上传
    }

    /**
     * 粘包 - 解码
     *
     * @param in
     * @return 粘包完成, 返回原始字节数据
     */
    protected abstract byte[] packetSplicing(ByteBuf in);

    /**
     * 解码 - 反序列化
     *
     * @param bytes
     * @param out
     * @return true 继续处理数据; false 结束本地处理, 执行一下一步业务
     */
    protected abstract boolean deserialization(byte[] bytes, List<Object> out);
}
