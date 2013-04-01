package org.jabox.utils;

import java.util.Date;

/**
 *
 */
public class Timestamp {
    /**
     * @return a timestamp of current time.
     */
    public static String now() {
        return String.valueOf(new Date().getTime());
    }
}
