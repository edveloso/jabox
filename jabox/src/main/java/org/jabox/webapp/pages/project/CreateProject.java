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
package org.jabox.webapp.pages.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.persistence.provider.ProjectXstreamDao;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.jabox.application.ICreateProjectUtil;
import org.jabox.model.Project;
import org.jabox.webapp.pages.BaseProjectsPage;
import org.jabox.webapp.validation.ShinyForm;

import com.google.inject.Inject;

@AuthorizeInstantiation("ADMIN")
public class CreateProject extends BaseProjectsPage {
    private static final long serialVersionUID = -6051173629887691918L;

    @Inject
    private ICreateProjectUtil _createProjectUtil;

    public CreateProject() {
        final Project _project = new Project();

        // Add a form with an onSumbit implementation that sets a message
        Form<Project> form = new ShinyForm<Project>("form") {
            private static final long serialVersionUID =
                -662744155604166387L;

            @Override
            protected void onSubmit() {
                // We need to persist twice because the id is necessary for the
                // creation of the project.
                ProjectXstreamDao.persist(_project);
                _createProjectUtil.createProject(_project);
                ProjectXstreamDao.persist(_project);
                info("Project \"" + _project.getName() + "\" Created.");
            }
        };

        form.setModel(new CompoundPropertyModel<Project>(_project));
        add(form);

        // Add a FeedbackPanel for displaying form messages
        add(new FeedbackPanel("feedback",
            new ComponentFeedbackMessageFilter(form)));

        // Name
        FormComponent<String> name = new RequiredTextField<String>("name");
        form.add(name);
        name.add(new CreateProjectValidator());
        name.add(new PatternValidator("[a-z0-9-]*"));
        name.add(new StringValidator.MaximumLengthValidator(24));

        // Description
        RequiredTextField<Project> description =
            new RequiredTextField<Project>("description");
        form.add(description);

        RequiredTextField<String> archetype =
            new RequiredTextField<String>("archetype");
        String dataSource = generateDataSource();
        archetype.add(new AttributeAppender("data-source",
            new Model<String>(dataSource), " "));
        form.add(archetype);

        // Description
        // RequiredTextField<Project> mavenArchetype =
        // new RequiredTextField<Project>("mavenArchetype");
        // form.add(mavenArchetype);

        form.add(new TextField<Project>("sourceEncoding"));
        form.add(new CheckBox("signArtifactReleases"));
    }

    private String generateDataSource() {
        StringBuffer data = new StringBuffer();
        data.append("[");
        List<String> archetypes = new ArrayList<String>();
        InputStream is =
            CreateProject.class
                .getResourceAsStream("/org/jabox/webapp/pages/project/archetypes.csv");
        BufferedReader reader =
            new BufferedReader(new InputStreamReader(is));
        String thisLine;
        try {
            while ((thisLine = reader.readLine()) != null) {
                archetypes.add(thisLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean addComma = false;
        for (String archetype : archetypes) {
            if (!addComma) {
                addComma = true;
            } else {
                data.append(",");
            }
            data.append("\"");
            data.append(archetype);
            data.append("\"");
        }

        data.append("]");
        return data.toString();
    }

}
