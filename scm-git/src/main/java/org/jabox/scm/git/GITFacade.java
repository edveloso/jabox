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
package org.jabox.scm.git;

import java.io.File;
import java.io.IOException;

import org.jabox.model.Project;

public class GITFacade {

    public GITFacade() {
    }

    public boolean validate(final String url, final String username,
            final String password) throws IOException {
        return true;
    }

    /**
     * Checks out the base-dir of the git
     * 
     * @param storePath
     *            the path where to store the git base-dir.
     */
    public void checkoutBaseDir(final File storePath,
            final IGITConnectorConfig gitc) {

        GITRepository.initialize();
    }

    public void commitProject(final Project project, final File tmpDir,
            final IGITConnectorConfig svnc) {
        File projectDir = new File(tmpDir, project.getName());
        Executor.exec("git init", null, projectDir);
        Executor.exec("git  add -A", null, projectDir);
        Executor.exec("git commit -m Initial", null, projectDir);
    }
}
