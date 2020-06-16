package org.sleeve.request;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/15 16:57
 */
public class HttpRequestLine {
    private String method;
    private String path;
    private String protocol;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
