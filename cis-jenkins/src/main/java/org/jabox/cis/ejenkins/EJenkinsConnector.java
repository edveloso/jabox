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

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.jabox.cis.jenkins.JenkinsConnector;
import org.jabox.model.DeployerConfig;
import org.jabox.model.Server;

/**
 * TODO: Find a way to pass credentials to Jenkins: by doing a post here:
 * http://localhost
 * :9080/jenkins/scm/SubversionSCM/enterCredential?_httpUrlOfSubversion_
 * 
 * @author Administrator
 */
public class EJenkinsConnector extends JenkinsConnector {
    public static final String ID = "plugin.cis.ejenkins";

    @Override
    public String getName() {
        return "Embedded Jenkins";
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public DeployerConfig newConfig() {
        return new EJenkinsConnectorConfig();
    }

    @Override
    public Component newEditor(final String id, final IModel<Server> model) {
        return new EJenkinsConnectorEditor(id, model);
    }
}
