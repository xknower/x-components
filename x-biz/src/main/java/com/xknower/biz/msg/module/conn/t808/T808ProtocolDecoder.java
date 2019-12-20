package com.xknower.biz.msg.module.conn.t808;

import com.xknower.biz.msg.module.T808Msg;
import com.xknower.thrid.netty.module.decoder.ProtocolDecoder;
import io.netty.buffer.ByteBuf;

import java.util.List;

/**
 * 808协议, 消息解码器
 *
 * @author xknower
 */
public class T808ProtocolDecoder extends ProtocolDecoder {

    @Override
    protected byte[] packetSplicing(ByteBuf in) {
        in.markReaderIndex();
        while (in.isReadable()) {
            //
            in.markReaderIndex();
            int packetBeginIndex = in.readerIndex();
            byte tag = in.readByte();
            // 搜索包的开始位置
            if (tag == 0x7E && in.isReadable()) {
                tag = in.readByte();
                // 防止是两个0x7E,取后面的为包的开始位置
                // 寻找包的结束
                while (tag != 0x7E) {
                    if (!in.isReadable()) {
                        // 没有找到结束包，等待下一次包
                        in.resetReaderIndex();
                        return null;
                    }
                    tag = in.readByte();
                }
                int pos = in.readerIndex();
                int packetLength = pos - packetBeginIndex;
                if (packetLength > 1) {
                    byte[] bytes = new byte[packetLength];
                    in.resetReaderIndex();
                    in.readBytes(bytes);

                    // 判断是否符合最小数据长度 (12字节)
                    if (bytes.length < 12) {
                        return null;
                    }

                    return bytes;
                } else {
                    // 说明是两个 0x7E
                    in.resetReaderIndex();
                    // 两个7E说明前面是包尾，后面是包头
                    in.readByte();
                }
            } else {
                return null;
            }
        }

        return null;
    }

    @Override
    protected boolean deserialization(byte[] bytes, List<Object> out) {
        try {
            // 解码消息
            T808Msg msg = new T808Msg();
            msg.deserialization(bytes);
            out.add(msg);
        } catch (Exception e) {
            // 继续粘包并解析
            return false;
        }

        // 解析一个消息后, 进行业务处理
        return false;
    }
}
