package org.sleeve;

import java.io.IOException;
import java.io.InputStream;

/**
 * 获得inputstream，从流中创建对象，获取HTTP的请求数据，并负责解析
 *
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/12 17:00
 */
public class Request {
   private InputStream inputStream;
   private String uri;

   public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void parse() {
        StringBuilder request = new StringBuilder();
        int len=-1;
        byte[] bytes = new byte[1024];
        try {
            len = inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //把字节数组变成字符串
        request.append(new String(bytes,0,len));
        System.out.print(request.toString());
        this.uri=parseUri(request.toString());
    }
    //使用私有方法处理uri
    private String parseUri(String request) {
        int firstIndex = request.indexOf('/');
        int endIndex =request.indexOf(" ", firstIndex);
        return request.substring(firstIndex, endIndex);
   }

    public String getUri() {
       return this.uri;
    }
}
