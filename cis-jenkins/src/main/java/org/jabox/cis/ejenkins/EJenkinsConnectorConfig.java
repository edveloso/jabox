/*
 * Jabox Open Source Version
 * Copyright (C) 2009-2010 Dimitris Kapanidis                                                                                                                          
 * 
 * This file is part of Jabox
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package org.jabox.cis.ejenkins;

import org.jabox.cis.jenkins.JenkinsConnectorConfig;
import org.jabox.model.Server;
import org.jabox.utils.LocalHostName;

public class EJenkinsConnectorConfig extends JenkinsConnectorConfig {
    private static final long serialVersionUID = -6696934779273872749L;

    public String username;

    public String password;

    public EJenkinsConnectorConfig() {
        pluginId = EJenkinsConnector.ID;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setServer(final Server server) {
        super.setServer(server);
        if (server != null) {
            server.setUrl("http://" + LocalHostName.getLocalHostname()
                + ":8080/jenkins/");
        }
    }

    @Override
    public String getPassword() {
        return password;
    }
}
