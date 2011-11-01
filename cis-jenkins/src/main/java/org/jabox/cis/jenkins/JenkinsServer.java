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
package org.jabox.cis.jenkins;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.FileUtils;
import org.jabox.apis.embedded.AbstractEmbeddedServer;
import org.jabox.environment.Environment;
import org.jabox.model.Plugin;
import org.jabox.utils.DownloadHelper;

public class JenkinsServer extends AbstractEmbeddedServer {
    final String URL =
        "http://mirrors.jenkins-ci.org/war/1.436/jenkins.war";

    public static void main(final String[] args) throws Exception {
        new JenkinsServer().startServerAndWait();
    }

    public String getServerName() {
        return "jenkins";
    }

    @Override
    public String getWarPath() {
        injectPlugins();
        injectConfigurations();
        File downloadsDir = Environment.getDownloadsDir();

        // Download the jenkins.war
        File war = new File(downloadsDir, "jenkins.war");
        if (!war.exists()) {
            DownloadHelper.downloadFile(URL, war);
        }
        return war.getAbsolutePath();
    }

    private void injectConfigurations() {
        injectConfiguration("hudson.tasks.Maven.xml");
        injectConfiguration("hudson.plugins.sonar.SonarPublisher.xml");
    }

    public List<Plugin> plugins = getDefaultPlugins();

    public void injectPlugins() {
        for (Plugin plugin : plugins) {
            injectPlugin(plugin.getName(), plugin.getVersion());
        }
    }

    /**
     * @return
     */
    private List<Plugin> getDefaultPlugins() {
        List<Plugin> defaultPlugins =
            new ArrayList<Plugin>();

        defaultPlugins.add(new Plugin("analysis-core", "1.14"));
        defaultPlugins.add(new Plugin("dry", "1.5"));
        defaultPlugins.add(new Plugin("pmd", "3.10"));
        defaultPlugins.add(new Plugin("findbugs", "4.14"));
        defaultPlugins.add(new Plugin("checkstyle", "3.10"));
        defaultPlugins.add(new Plugin("m2release", "0.6.1"));
        defaultPlugins.add(new Plugin("redmine", "0.9"));
        defaultPlugins.add(new Plugin("git", "1.1.3"));
        defaultPlugins.add(new Plugin("claim", "1.7"));
        defaultPlugins.add(new Plugin("ci-game", "1.17"));
        defaultPlugins.add(new Plugin("sonar", "1.6.1"));

        return defaultPlugins;
    }

    private static void injectConfiguration(final String resource) {
        URL res = JenkinsServer.class.getResource(resource);
        File dest = new File(Environment.getHudsonHomeDir(), resource);
        if (!dest.exists()) {
            try {
                FileUtils.copyURLToFile(res, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectPlugin(final String artifactId,
            final String version) {
        File dest = new File(getJenkinsPluginDir(), artifactId + ".hpi");
        if (!dest.exists()) {
            DownloadHelper.downloadFile(
                "http://updates.jenkins-ci.org/download/plugins/"
                    + artifactId + "/" + version + "/" + artifactId
                    + ".hpi", dest);
        }
    }

    private static File getJenkinsPluginDir() {
        return new File(Environment.getHudsonHomeDir(), "plugins");
    }
}
