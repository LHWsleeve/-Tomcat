package org.sleeve;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/13 17:38
 */
public class StaticResourceProcessor {
    public void process(Request request, Response response){
        response.setRequest(request);
        response.sendStaticResource();
    }
}
