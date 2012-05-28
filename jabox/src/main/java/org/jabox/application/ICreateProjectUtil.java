package org.jabox.application;

import java.io.IOException;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.artifact.InvalidRepositoryException;
import org.jabox.apis.scm.SCMException;
import org.jabox.model.Project;
import org.xml.sax.SAXException;

import com.google.inject.ImplementedBy;

@ImplementedBy(CreateProjectUtil.class)
public interface ICreateProjectUtil {

    public abstract void createProject(final Project project)
            throws InvalidRepositoryException, SAXException, SCMException,
            IOException, MavenExecutionException;
}
