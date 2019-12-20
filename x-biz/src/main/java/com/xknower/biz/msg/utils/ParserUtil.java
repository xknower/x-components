package com.xknower.biz.msg.utils;

/**
 * 消息解析工具类
 *
 * @author xknower
 */
public class ParserUtil {

    /**
     * [7E 808消息] 转义还原
     */
    public static byte[] decodeEscape(byte[] data) {
        MyBuffer buff = new MyBuffer();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0x7D) {
                if (data[i + 1] == 0x01) {
                    buff.put((byte) 0x7D);
                    i++;
                } else if (data[i + 1] == 0x02) {
                    buff.put((byte) 0x7E);
                    i++;
                }
            } else {
                buff.put(data[i]);
            }
        }
        byte[] a = buff.array();
        return a;
    }

    /**
     * [7E 808消息] 转义封装
     */
    public static byte[] encodeEscape(byte[] data) {
        MyBuffer tmp = new MyBuffer();
        for (int j = 0; j < data.length; j++) {
            if (data[j] == 0x7D) {
                tmp.put((byte) 0x7D);
                tmp.put((byte) 0x01);
            } else if (data[j] == 0x7E) {
                tmp.put((byte) 0x7D);
                tmp.put((byte) 0x02);
            } else {
                tmp.put(data[j]);
            }
        }
        return tmp.array();
    }
}
