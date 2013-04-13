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
package org.jabox.mrm.nexus;

import java.io.File;

import org.jabox.apis.embedded.AbstractEmbeddedServer;
import org.jabox.environment.Environment;
import org.jabox.utils.DownloadHelper;

/**
 * 
 */
public class NexusServer extends AbstractEmbeddedServer {
    private static final long serialVersionUID = -2159722560362413951L;

    private final String version = "2.3";

    @Override
    public String getServerName() {
        return "nexus";
    }

    @Override
    public String getWarPath() {
        File downloadsDir = Environment.getDownloadsDir();

        // Download the nexus.war
        File zipFile = new File(downloadsDir, "nexus.war");
        String url =
            "http://download.sonatype.com/nexus/oss/nexus-" + version
                + ".war";
        zipFile = DownloadHelper.downloadFile(url, zipFile);
        return zipFile.getAbsolutePath();
    }
}
