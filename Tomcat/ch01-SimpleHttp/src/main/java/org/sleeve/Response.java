package org.sleeve;
import java.io.*;

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
        this.request = request;
    }

    /**
     * 获取资源并响应
     */
    public void sendStaticResource() {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis=null;
        try{
            //根目录+uri组成请求路径
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()){
                fis = new FileInputStream(file);
                int read = fis.read(bytes,0,BUFFER_SIZE);
                while (read!=-1){
                    outputStream.write(bytes,0,read);
                    read=fis.read(bytes,0,BUFFER_SIZE);
                }
            } else {
                System.out.print("----------没找到文件----------");
                String message = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>\r\n";
                outputStream.write(message.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
