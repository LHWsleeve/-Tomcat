package org.sleeve;

import sun.security.mscapi.PRNG;

import java.io.OutputStream;

/**
 * 响应HTTP的处理结果
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/12 17:01
 */
public class Response {
    private OutputStream outputStream;
    private static final int BUFFER_SIZE = 1024;
    private Request request;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
    }

    public void sendStaticResource() {
    }
}
