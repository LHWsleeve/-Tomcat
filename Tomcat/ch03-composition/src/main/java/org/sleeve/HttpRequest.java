package org.sleeve;

import org.sleeve.request.HttpRequestLine;
import org.sleeve.request.ParameterMap;
import org.sleeve.request.RequestStream;
import org.sleeve.request.RequestUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

/**
 * 1. d读取套接字的输入流
 * 2.解析请求行
 * 3. 解析请求头
 * 4. 解析Cooike
 * 5. 获取参数
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/16 14:45
 */
public class HttpRequest implements HttpServletRequest {
    private String path;
    private SocketInputStream socketInputStream;
    private HttpRequestLine requestLine;
    private String queryString;
    private String jsessionid;
    // 判断jsessionid是在cookie里还是url里
    private boolean requestedSessionIdFromUrl;
    private String method;
    private String scheme;
    private String protocol;
    private Map<String, String> headers = new HashMap<String, String>();
    private String contentType;
    private List<Cookie> cookies = new ArrayList<Cookie>();
    private boolean parsedParameter;
    private Map<String, String> parameterMap = new ParameterMap();
    private boolean requestedSessionIdFromCookie;
    private int contentLength;

    private RequestStream requestStream;
    public HttpRequest(SocketInputStream socketInputStream) {
        this.requestStream = new RequestStream(this);
        this.socketInputStream = socketInputStream;
    }
    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpRequestLine getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(HttpRequestLine requestLine) {
        this.requestLine = requestLine;
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String s) {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        return null;
    }

    @Override
    public Enumeration getHeaders(String s) {
        return null;
    }

    @Override
    public Enumeration getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(String s) {
        return 0;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return this.jsessionid;
    }

    @Override
    public String getRequestURI() {
        return null;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean b) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return this.requestedSessionIdFromCookie;
    }

    public void setRequestedSessionIdFromCookie(boolean requestedSessionIdFromCookie) {
        this.requestedSessionIdFromCookie = requestedSessionIdFromCookie;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return this.requestedSessionIdFromUrl;
    }

    @Deprecated
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return this.requestedSessionIdFromUrl;
    }

    public void setRequestedSessionIdFromUrl(boolean requestedSessionIdFromUrl) {
        this.requestedSessionIdFromUrl = requestedSessionIdFromUrl;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return this.contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String value) {
        this.contentType = value;
    }

    @Override
    public ServletInputStream getInputStream() {
        return this.requestStream;
    }

    @Override
    public String getParameter(String s) {
        if (!parsedParameter) {
            parseParameter();
        }
        return this.parameterMap.get(s);
    }

    /**
     * 解析请求参数
     */
    private void parseParameter() {
        String encoding = getCharacterEncoding();

        if (encoding == null) {
            encoding = "ISO-8859-1";
        }
        if("POST".equals(getMethod()) && "application/x-www-form-urlencoded".equals(getContentType())){
            RequestUtil.parseRequest(this.parameterMap, queryString);
            ((ParameterMap) this.parameterMap).setLocked(true);
            // 已经解析过请求参数
            this.parsedParameter = true;
        }


    }

    @Override
    public Enumeration getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map getParameterMap() {
        return this.parameterMap;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Deprecated
    @Override
    public String getRealPath(String s) {
        return null;
    }

    public InputStream getOriginalStream() {
        return this.socketInputStream;
    }
}
