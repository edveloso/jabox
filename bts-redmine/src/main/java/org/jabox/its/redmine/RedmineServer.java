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
package org.jabox.its.redmine;

import java.io.File;

import org.jabox.apis.embedded.AbstractEmbeddedServer;
import org.jabox.environment.Environment;
import org.jabox.utils.DownloadHelper;

public class RedmineServer extends AbstractEmbeddedServer {
	private static final long serialVersionUID = 9207781259797681188L;
	private final String version = "1.3.0";

	public static void main(final String[] args) throws Exception {
		new RedmineServer().startServerAndWait();
	}

	@Override
	public String getServerName() {
		return "redmine";
	}

	@Override
	public String getWarPath() {
		File downloadsDir = Environment.getDownloadsDir();

		// Download the nexus.war
		File war = new File(downloadsDir, "redmine.war");
		String url = "http://www.jabox.org/repository/releases/org/redmine/redmine/"
				+ version + "/redmine-" + version + ".war";
		war = DownloadHelper.downloadFile(url, war);
		return war.getAbsolutePath();
	}
}
