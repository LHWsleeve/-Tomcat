package org.sleeve.processor;

import org.sleeve.HttpRequest;
import org.sleeve.HttpResponse;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/16 15:44
 */
public class StaticProcessor {
    public void process(HttpRequest request, HttpResponse response) {
        response.sendStaticResource();
    }
}
