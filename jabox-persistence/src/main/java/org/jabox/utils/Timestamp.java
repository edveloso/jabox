/**
 * $Id$
 * @author dimitris
 * @date   Apr 1, 2013 4:08:49 PM
 *
 * Copyright (C) 2013 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
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
