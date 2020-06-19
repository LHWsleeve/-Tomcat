package org.sleeve.processor;

import org.sleeve.HttpRequest;
import org.sleeve.HttpResponse;

import javax.servlet.Servlet;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static org.sleeve.startup.Bootstrap.WEB_ROOT;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/16 15:44
 */
public class ServletProcessor {
    public void process(HttpRequest request, HttpResponse response) throws MalformedURLException, ClassNotFoundException {
        String repositoryUrl = null;
        String uri = request.getPath();
        repositoryUrl = new URL("file", null, WEB_ROOT + File.separator).toString();
        ClassLoader loader = new URLClassLoader(new URL[]{new URL(repositoryUrl)});

        // 这里要给全限定名，否则无法加载到
        Class servletClass = loader.loadClass("org.sleeve." + uri.substring(uri.lastIndexOf("/") + 1));
        try {
            Servlet servlet = (Servlet)servletClass.getConstructor().newInstance();//
            servlet.service(request, response);

        } catch (Exception e) {
            e.printStackTrace();
    }

        response.finishResponse();
    }
}
