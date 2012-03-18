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
package org.jabox.its.chiliproject;

import java.io.File;

import org.jabox.apis.embedded.AbstractEmbeddedServer;
import org.jabox.environment.Environment;
import org.jabox.utils.DownloadHelper;

public class ChiliprojectServer extends AbstractEmbeddedServer {
	private static final long serialVersionUID = 9207781259797681188L;
	private final String version = "3.0.0";

	public static void main(final String[] args) throws Exception {
		new ChiliprojectServer().startServerAndWait();
	}

	@Override
	public String getServerName() {
		return "chiliProject";
	}

	@Override
	public String getWarPath() {
		File downloadsDir = Environment.getDownloadsDir();

		// Download the nexus.war
		File war = new File(downloadsDir, "chiliproject.war");
		String url = "http://www.jabox.org/repository/releases/org/chiliproject/chiliproject/"
				+ version + "/chiliproject-" + version + ".war";
		war = DownloadHelper.downloadFile(url, war);
		return war.getAbsolutePath();
	}
}
