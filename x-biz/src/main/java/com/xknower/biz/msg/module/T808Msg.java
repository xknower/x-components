package com.xknower.biz.msg.module;

import com.xknower.biz.msg.utils.CRCUtil;
import com.xknower.biz.msg.utils.ParserUtil;
import com.xknower.queue.module.IMsg;
import com.xknower.thrid.netty.module.ITMsg;
import org.apache.commons.codec.binary.Hex;

import java.io.Serializable;

/**
 * 车联网 808 协议, 实体类定义
 *
 * @author xknower
 */
public class T808Msg implements IMsg, ITMsg {

    /**
     * 消息报文头
     */
    private MsgHeader header = new MsgHeader();
    /**
     * 消息体
     */
    private IMsgBody IMsgBody;

    /**
     * 消息分包传输分包数据
     */
    private byte[] childPacket;
    /**
     * 错误记录
     */
    private String errMsg;
    /**
     * 原始数据
     */
    private byte[] data;

    public T808Msg() {

    }

    public T808Msg(String onlineNo, int msgType, byte[] bytes) {
        header = new MsgHeader();
        header.setOnlineNo(onlineNo);
        header.setMsgType(msgType);
        this.data = bytes;
    }

    public String getOnlineNo() {
        return header == null ? "" : header.getOnlineNo();
    }

    /**
     * 序列化, 编码对象为字节流
     *
     * @return
     */
    @Override
    public final byte[] serialization() {
        return new byte[0];
    }

    /**
     * 反序列化, 解码字节流为对象
     *
     * @param bytes 对象字节流
     */
    @Override
    public final void deserialization(byte[] bytes) {
        //
        data = bytes;

        // 转义还原
        byte[] validMessageBytes = ParserUtil.decodeEscape(bytes);

        // 检测校验码
        byte xor = CRCUtil.calcCrcXor(validMessageBytes, 1, validMessageBytes.length - 2);
        byte realXor = validMessageBytes[validMessageBytes.length - 2];
        if (xor != realXor) {
            errMsg = "校验码不正确";
            return;
        }

        try {
            // 数据长度, [首位标识符与检验字]
            int tempLen = validMessageBytes.length - 1 - 1 - 1;
            // 获取消息头 [12 无分包消息体为空]
            byte[] headerBytes = new byte[tempLen < 16 ? 12 : 16];
            System.arraycopy(validMessageBytes, 1, headerBytes, 0, headerBytes.length);
            //
            header.deserialization(headerBytes);

            // 定位消息体数据指针
            int startPoint = 17;
            if (!header.isPackage()) {
                // 不分包则少4个字节的分包信息
                startPoint = 13;
            }

            if (header.getMsgSize() > 0) {
                // 消息体长度大于零 -> 解析消息体 sourceData
                byte[] sourceData = new byte[header.getMsgSize()];
                System.arraycopy(validMessageBytes, startPoint, sourceData, 0, sourceData.length);
                if (header.isPackage()) {
                    // 分包的消息体是纯数据不进行解析, 保留在消息中.
                    childPacket = new byte[header.getMsgSize()];
                    System.arraycopy(sourceData, 0, childPacket, 0, header.getMsgSize());
                } else {
                    if (header.isEncryption()) {
                        // 消息加密
                        System.out.println(String.format("808消息加密传输 => [ %s ]", getHexData()));
                        return;
                    }

                    // 消息体数据解析
                    System.out.println(String.format("消息体解析 => %s | %s", header.getMsgType(), Hex.encodeHexString(sourceData)));
                }
                //
                System.out.println(String.format("消息解析完成 => %s -> %s -> %s", hash(), type(), getHexData()));
            }
        } catch (Exception e) {
            System.out.println(String.format("解析异常 => %s | %s -> %s | %s", hash(), type(), getHexData(), errMsg));
        }
    }

    public String getHexData() {
        return data != null ? Hex.encodeHexString(data) : "";
    }

    @Override
    public Serializable hash() {
        return getOnlineNo();
    }

    @Override
    public Serializable type() {
        return header != null ? header.getMsgType() : 0;
    }
}
