package org.sleeve.connector;
import org.sleeve.processor.HttpProcessor;
import javax.servlet.ServletException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *创建服务器的套接字，该套接字等待传入的HTTP请求。每个请求都是自己的专属线程
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/15 16:18
 */
public class HttpConnector implements Runnable {
    private HttpProcessor processor = new HttpProcessor();
    @Override
    public void run() {
        ServerSocket serverSocket=null;
        int port=10000;
        try{
            serverSocket= new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
            Socket conn = serverSocket.accept();
            try {
                processor.process(conn);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void start(){
        Thread thread = new Thread(this);
        thread.start();

    }
}
