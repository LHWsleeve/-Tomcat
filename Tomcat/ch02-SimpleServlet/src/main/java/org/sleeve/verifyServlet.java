package org.sleeve;
import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 测试servlet应用程序
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/13 16:59
 */
public class verifyServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.print("Servlet初始化");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.print("处理服务");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/plain");
        PrintWriter printWriter = servletResponse.getWriter();

        printWriter.println("scheme:" + servletRequest.getScheme()+"</br>");
        printWriter.println("protocol:" + servletRequest.getProtocol()+"</br>");
        printWriter.println("------------End----------------");

        printWriter.flush();
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.print("duang,销毁");
    }
}
