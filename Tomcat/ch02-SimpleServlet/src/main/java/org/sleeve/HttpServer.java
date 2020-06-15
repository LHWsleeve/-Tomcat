package org.sleeve;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/13 17:37
 */
public class HttpServer {
    static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    private StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
    private ServletProcessor servletProcessor = new ServletProcessor();
    public void await() throws IOException {
        // 监听8080端口
        ServerSocket socket = new ServerSocket(10000);
        while(true){
            // 同步阻塞等待请求
            Socket conn = socket.accept();
            Request request = new Request(conn.getInputStream());
            request.parse();

            Response response = new Response(conn.getOutputStream());

            try {
                ensureServlet(request, response);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

            conn.close();
        }
    }
    public static void main(String[] args) throws IOException {
        new HttpServer().await();
    }

    private void ensureServlet(Request request, Response response) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        if(request.getUri().contains("servlet")){
            servletProcessor.process(request, response);
        }else{
            staticResourceProcessor.process(request, response);
        }

    }

}
