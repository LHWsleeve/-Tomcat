package org.sleeve.request;

import java.util.Map;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/16 15:36
 */
public class RequestUtil {
    public static void parseRequest(Map<String, String> parameterMap, String data){
        String[] without1 = data.split("&");

        for (String s : without1) {
            String[] result = s.split("=");
            parameterMap.put(result[0], result[1]);
        }

    }
}
