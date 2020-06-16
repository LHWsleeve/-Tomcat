package org.sleeve.request;

import org.sleeve.HttpRequest;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/16 15:27
 */
public class RequestStream extends ServletInputStream {
    @Override
    public int read() throws IOException {
        if(closed){
            throw new IllegalStateException("InputStream已关闭......");
        }
        if(length >= 0 && hadReadLen >= length){
            return -1;
        }
        int ch = is.read();
        if(ch < 0){
            ++hadReadLen;
        }
        return ch;    }
    private InputStream is;
    private int length;
    private boolean closed;
    private int hadReadLen;

    public RequestStream(HttpRequest request){
        this.is = request.getOriginalStream();
        this.length = request.getContentLength();
    }

    @Override
    public void close() throws IOException {
        if(closed){
            throw new IllegalStateException("InputStream已关闭......");
        }
        while(true){
            int ch = is.read();
            if(ch < 0){
                break;
            }
        }
        closed = true;
    }
}
