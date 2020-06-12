package org.sleeve;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建Server实例，调用await方法，等待客户端连接
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/12 16:40
 *
 */
public class HttpServer {
    //WEB_ROOT初始值，加载静态资源的默认地址
    public static final String WEB_ROOT= System.getProperty("user.dir")+ File.separator+"webroot";
    //默认关闭服务器命令
    private static  final String SHUTDOWN_COMMAND="/SHUTDOWN";
    //服务器状态标记
    private  static boolean shutdown = false;

    public static void main(String[] args) throws IOException {
        //监听10000端口
        ServerSocket socket = new ServerSocket(10000);
        //服务器未关闭则一直循环，同步阻塞请求...NIO就算了自己写要炸裂
        while (!shutdown){
            Socket conn = socket.accept();
            InputStream inputStream = conn.getInputStream();
            OutputStream outputStream = conn.getOutputStream();
            //创建请求对象,并且解析HTTP的请求
         Request request = new Request(inputStream);
         request.parse();
         //创建响应对象,请求传入响应进行处理，发送请求的静态资源
         Response response = new Response(outputStream);
         response.setRequest(request);
         response.sendStaticResource();
         //关闭连接
         conn.close();
         //如果当前uri中由shutdown关闭命令，则赋值
         shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
        }
    }

}
