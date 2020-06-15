package org.sleeve.processor;

import org.sleeve.SocketInputStream;

import java.net.Socket;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/15 16:30
 */
public class HttpProcessor {
    /**
     * 接受传入HTTP请求的套接字并完成以下操作：
     * 1. 创建一个rquest
     * 2.创建一个reponse
     * 3. 解析HTTP请求的第一行内容和请求头信息，填充request对象
     * 4. 将request和reponse对象传递给servletProssor或StaticProcessor的process()方法。
     * @param conn
     */
    public void process(Socket conn) {
        SocketInputStream
    }
}
