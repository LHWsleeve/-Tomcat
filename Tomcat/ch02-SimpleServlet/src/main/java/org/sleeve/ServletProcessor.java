package org.sleeve;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import static org.sleeve.HttpServer.WEB_ROOT;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/15 8:23
 */
public class ServletProcessor {
    public void process(Request request, Response response) throws ClassNotFoundException, IOException {
        String repositoryUrl = null;
        String uri = request.getUri();
        repositoryUrl = new URL("file", null, WEB_ROOT + File.separator).toString();
        ClassLoader loader = new URLClassLoader(new URL[]{new URL(repositoryUrl)});
        // 一定要是全限定名，否则无法加载到。直接去校对src.main.java.xxxx。从xxx开始的全路径
        Class servletClass = loader.loadClass("org.sleeve." + uri.substring(uri.lastIndexOf("/") + 1));
        Servlet servlet = null;
        try {
            servlet = (Servlet)servletClass.getConstructor().newInstance();
            servlet.service(new RequestFacade(request), new ResponseFacade(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
