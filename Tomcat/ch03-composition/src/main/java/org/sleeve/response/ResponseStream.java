package org.sleeve.response;

import org.sleeve.HttpResponse;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/16 15:39
 */
public class ResponseStream extends ServletOutputStream {
    private HttpResponse response;
    private OutputStream outputStream;
    private boolean isFirst = true;
    public ResponseStream(HttpResponse response) {
        this.response = response;
        this.outputStream = response.getOriginalOutputStream();
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }
}
