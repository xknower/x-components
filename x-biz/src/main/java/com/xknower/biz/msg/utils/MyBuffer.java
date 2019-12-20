package com.xknower.biz.msg.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * 消息字节缓冲区数据解析工具类
 *
 * @author xknower
 */
public class MyBuffer {

    /**
     * 缓冲区
     */
    private ByteBuffer buff;

    public MyBuffer() {
        buff = ByteBuffer.allocate(1536);
        buff.mark();
    }

    public MyBuffer(int len) {
        buff = ByteBuffer.allocate(len);
        buff.mark();
    }

    public MyBuffer(byte[] bytes) {
        if (bytes.length > 1024) {
            buff = ByteBuffer.allocate(bytes.length + 100);
        } else {
            buff = ByteBuffer.allocate(1024);
        }
        buff.mark();
        buff.put(bytes);
        buff.limit(bytes.length);
        buff.reset();
    }

    /**
     * 清除
     */
    public void clear() {
        // limit=capacity , position=0,重置mark
        buff.clear();
        // 取当前的position的快照标记mark
        buff.mark();
    }

    /**
     * 判断缓冲区是否还有数据
     */
    public boolean hasRemain() {
        return buff.remaining() > 0;
    }

    /********** ********** 将数据放入缓冲区 ********** **********/

    public void put(byte a) {
        buff.put(a);
    }

    public void put(short a) {
        buff.putShort(a);
    }

    public void put(byte[] byteData) {
        buff.put(byteData);
    }

    public void put(int intData) {
        buff.putInt(intData);
    }

    public void putShort(int intShortData) {
        buff.putShort((short) intShortData);
    }

    public void put(String str) {
        try {
            // US-ASCII
            buff.put(str.getBytes("gbk"));
        } catch (UnsupportedEncodingException e) {
            // String.format("gbk put")
        }
    }

    public void put(String str, int len) {
        try {
            byte[] result = new byte[len];
            byte[] b = str.getBytes("gbk");
            System.arraycopy(b, 0, result, 0, b.length);
            for (int m = b.length; m < len; m++) {
                result[m] = 0;
            }
            buff.put(result);
        } catch (UnsupportedEncodingException e) {
            // String.format("gbk put")
        }
    }

    /********** ********** 缓冲区获取格式数据 ********** **********/

    /**
     * 取一定字节长度的byte字节数组
     */
    public byte[] gets(int len) {
        byte[] data = new byte[len];
        buff.get(data);
        return data;
    }

    /**
     * 取出一定字节长度数据, 并字节格式化成字符串
     */
    public String getFormatString(int len, String format) {
        StringBuffer sb = new StringBuffer();
        byte[] data = gets(len);
        for (int i = 0; i < data.length; i++) {
            sb.append(String.format(format, data[i]));
        }
        return sb.toString();
    }

    /**
     * byte - 1 字节
     */
    public byte get() {
        return buff.get();
    }

    /**
     * Short 长度 - 2字节
     */
    public short getShort() {
        return buff.getShort();
    }

    /**
     * Int 长度 - 4字节
     */
    public int getInt() {
        return buff.getInt();
    }

    /**
     * hex转浮点
     */
    public float getFloat() {
        return buff.getFloat();
    }

    /**
     * 1、字节 将data字节型数据转换为0~255 (0xFF 即BYTE)
     */
    public int getUnsignedByte() {
        return buff.get() & 0x0FF;
    }

    /**
     * 2、字 将data字节型数据转换为0~65535 (0xFFFF 即 WORD)
     */
    public int getUnsignedShort() {
        return buff.getShort() & 0xffff;
    }

    /**
     * 3、双字 DWORD
     */
    public long getUnsignedInt() {
        return (buff.getInt() & 0x0FFFFFFFF);
    }

    public byte[] array() {
        int pos = buff.position();
        byte[] data = new byte[pos];
        buff.reset();
        buff.get(data);
        return data;
    }

    /**
     * 设置数据定位指针
     *
     * @param position
     */
    public void setPosition(int position) {
        buff.position(position);
    }
}
