package org.sleeve.startup;

import org.sleeve.connector.HttpConnector;

/**
 * @author Sleeve
 * @version 1.0
 * @date 2020/6/15 16:18
 */
public class Bootstrap {
    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }
}
