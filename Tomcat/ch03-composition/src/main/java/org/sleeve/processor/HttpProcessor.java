package org.sleeve.processor;

import org.sleeve.HttpRequest;
import org.sleeve.HttpResponse;
import org.sleeve.SocketInputStream;
import org.sleeve.request.HttpHeader;
import org.sleeve.request.HttpRequestLine;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void process(Socket conn) throws IOException, ServletException {
        SocketInputStream inputStream = new SocketInputStream(conn.getInputStream());
        OutputStream outputStream = conn.getOutputStream();
        //从创建请求对象
        HttpRequest request = new HttpRequest(inputStream);
        //创建响应对象
        HttpResponse reponse = new HttpResponse(outputStream);
        
        reponse.setRequest(request);
        //解析Request
        parseRequest(request,inputStream);
        //解析请求头
        parseHeader(request,inputStream);

        if (request.getPath().startsWith("/servlet")){
            ServletProcessor processor = new ServletProcessor();
            try {
                processor.process(request,reponse);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            StaticProcessor processor = new StaticProcessor();
            processor.process(request,reponse);
        }
        conn.close();
    }

    private void parseRequest(HttpRequest request, SocketInputStream inputStream) throws ServletException {
        HttpRequestLine requestLine = new HttpRequestLine();
        request.setRequestLine(requestLine);

        // 读取请求行
        inputStream.readRequestLine(requestLine);

        String method = new String(requestLine.getMethod());
        String path = new String(requestLine.getPath());
        String protocol = new String(requestLine.getProtocol());

        if (method.length() < 1) {
            throw new ServletException("Missing HTTP Request Method");
        }
        if (path.length() < 1) {
            throw new ServletException("Missing HTTP Request URI");
        }
        if (protocol.length() < 1) {
            throw new ServletException("Missing HTTP Request Version");
        }

        // 处理请求
        int queryIndex = path.indexOf("?");
        if (queryIndex >= 0) {
            // 取问号后面的一系列字符串，作为查询参数
            request.setQueryString(path.substring(queryIndex + 1, path.length()));
            // 取问号前的一系列字符串作为路径，不包含问号
            path = path.substring(0, queryIndex);
        } else {
            request.setQueryString(null);
        }

        // 处理绝对地址
        if (!path.startsWith("/")) {
            int pos = path.indexOf("://");
            if (pos != -1) {
                pos = path.indexOf("/", pos + 3);
                if (pos == -1) {
                    path = "";
                } else {
                    path = path.substring(pos);
                }
            }
        }


        // 如果jsessionid出现在url栏里(客户端禁止了cookie的使用)，那么就可以执行下面的方法
        // 注意最新的Http协议，已经将输入参数（使用“;”)移除了
        String jsessionidMatch = ";jsessionid=";
        // 获取第一个分号下标
        int semicolon = path.indexOf(jsessionidMatch);
        if(semicolon != -1){
            // 获取jsessionidMatch后的内容
            String rest = path.substring(semicolon + jsessionidMatch.length());
            // 如果还能找到下一个 *输入参数*
            int semicolon2 = rest.indexOf(";");
            // 取下一个“;”前的内容
            if(semicolon2 > 0){
                request.setJsessionid(rest.substring(0, semicolon2));
            }else{
                // 不用考虑问号的情况，问号已经被处理
                request.setJsessionid(rest);
                rest = "";
            }
            request.setRequestedSessionIdFromUrl(true);
            path = path.substring(0, semicolon) + rest;
        }else{
            request.setRequestedSessionIdFromUrl(false);
            request.setJsessionid(null);
        }

        String normalizePath = normalize(path);
        if(normalizePath == null){
            throw new RuntimeException("");
        }else{
            request.setPath(normalizePath);
        }

        request.setMethod(method);
        request.setProtocol(protocol);
        request.setScheme("HTTP");
    }

    private void parseHeader(HttpRequest request, SocketInputStream inputStream) {
            HttpHeader header;
        do {
            header = new HttpHeader();
            inputStream.readHeader(header);

            if("Content-Length".equals(header.getKey())){
                if(header.getValue() != null){
                    request.setContentLength(Integer.parseInt(header.getValue().trim()));
                }else{
                    request.setContentLength(0);
                }
            }else if("Content-Type".equals(header.getKey())){
                request.setContentType(header.getValue());
            }else if("Cookie".equals(header.getKey())){
                Cookie[] cookies = parseCookies(header.getValue());
                for (Cookie cookie : cookies) {
                    if("jsessionid".equals(cookie.getName())){
                        if(!request.isRequestedSessionIdFromCookie()){
                            request.setJsessionid(cookie.getValue());
                            request.setRequestedSessionIdFromUrl(false);
                            request.setRequestedSessionIdFromCookie(true);
                        }
                    }
                    request.addCookie(cookie);
                }
            }
            request.addHeader(header.getKey(), header.getValue());
        }while(header.getKey() == null && header.getValue() == null);

    }
    private Cookie[] parseCookies(String cookieString){
        Pattern pattern = Pattern.compile("(.*)=(,*);");
        Matcher matcher = pattern.matcher(cookieString);
        List<Cookie> cookies = new ArrayList<Cookie>();
        if(matcher.matches()){
            for (int i = 1; i < matcher.groupCount(); i += 2) {
                String key = matcher.group(i);
                String value = matcher.group(i + 1);
                cookies.add(new Cookie(key, value));
            }
        }
        return (Cookie[])cookies.toArray();
    }

    private String normalize(String path) {
        return path.replaceAll("\\\\", "/");
    }

}
