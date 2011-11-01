/**
 * $Id$
 * @author dkapanidis
 * @date   Nov 1, 2011 12:38:50 PM
 *
 * Copyright (C) 2011 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package org.jabox.model;

/**
 *
 */
public class Plugin {
    private String name;

    private String version;

    /**
     * @param string
     * @param string2
     */
    public Plugin(final String name, final String version) {
        setName(name);
        setVersion(version);
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param version
     *            The version to set.
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * @return Returns the version.
     */
    public String getVersion() {
        return version;
    }

}
