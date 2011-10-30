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

import org.codehaus.plexus.util.FileUtils;
import org.jabox.apis.embedded.AbstractEmbeddedServer;
import org.jabox.environment.Environment;
import org.jabox.maven.helper.MavenDownloader;
import org.jabox.utils.DownloadHelper;

public class JenkinsServer extends AbstractEmbeddedServer {
	final String URL = "http://mirrors.jenkins-ci.org/war/1.436/jenkins.war";

	public static void main(final String[] args) throws Exception {
		new JenkinsServer().startServerAndWait();
	}

	public String getServerName() {
		return "jenkins";
	}

	public String getWarPath() {
		injectPlugins();
		File downloadsDir = Environment.getDownloadsDir();

		// Download the jenkins.war
		File war = new File(downloadsDir, "jenkins.war");
		if (!war.exists()) {
			DownloadHelper.downloadFile(URL, war);
		}
		return war.getAbsolutePath();
	}

	public static void injectPlugins() {
		injectPlugin("org.jvnet.jenkins.plugins", "analysis-core", "1.14");
		injectPlugin("org.jvnet.jenkins.plugins", "dry", "1.5");
		injectPlugin("org.jvnet.jenkins.plugins", "pmd", "3.10");
		injectPlugin("org.jvnet.jenkins.plugins", "findbugs", "4.14");
		injectPlugin("org.jvnet.jenkins.plugins", "checkstyle", "3.10");
		injectPlugin("org.jvnet.jenkins.plugins.m2release", "m2release", "0.6.1");
		injectPlugin("org.jvnet.jenkins.plugins", "redmine", "0.9");
		injectPlugin("org.jvnet.jenkins.plugins", "git", "1.1.3");
		injectPlugin("org.jvnet.jenkins.plugins", "claim", "1.7");
		injectPlugin("org.jvnet.jenkins.plugins", "ci-game", "1.17");
		injectPlugin("org.jvnet.jenkins.plugins", "sonar", "1.6.1");
		injectConfiguration("jenkins.tasks.Maven.xml");
		injectConfiguration("jenkins.plugins.sonar.SonarPublisher.xml");
	}

	private static void injectConfiguration(String resource) {
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

	private static void injectPlugin(String groupId, String artifactId,
			String version) {
		File plugin = MavenDownloader.downloadArtifact(groupId, artifactId,
				version, "hpi");
		try {
			FileUtils.copyFile(plugin, new File(getJenkinsPluginDir(),
					stripVersion(plugin.getName())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static String stripVersion(String name) {
		String replaceAll = name.replaceAll("-[^-]*.hpi", ".hpi");
		return replaceAll;
	}

	private static File getJenkinsPluginDir() {
		return new File(Environment.getHudsonHomeDir(), "plugins");
	}
}
