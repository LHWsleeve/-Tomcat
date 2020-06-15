package org.sleeve;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

import static org.sleeve.HttpServer.WEB_ROOT;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/13 17:37
 */
public class Response implements ServletResponse {
    private OutputStream os;
    private static final int BUFFER_SIZE = 1024;

    public Response(OutputStream os) {
        this.os = os;
    }

    private Request request;

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            String header = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 14\r\n" +
                    "\r\n";

            File file = new File(WEB_ROOT + File.separator + request.getUri());
            if (file.exists()) {
                os.write(header.getBytes());
                fis = new FileInputStream(file);
                int len = fis.read(bytes);
                while (len != -1) {
                    os.write(bytes, 0, len);
                    len = fis.read(bytes);
                }
            } else {
                System.out.println("-------没有找到文件--------");
                String message = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>\r\n";
                os.write(message.getBytes());
                os.flush();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
