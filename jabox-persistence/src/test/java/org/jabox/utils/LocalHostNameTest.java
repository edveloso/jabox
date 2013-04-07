package org.jabox.utils;

import org.junit.Test;

public class LocalHostNameTest {

    @Test
    public void testGetLocalHostname() {
        String localHostname = LocalHostName.getLocalHostname();
        System.out.println(localHostname);
    }
}
