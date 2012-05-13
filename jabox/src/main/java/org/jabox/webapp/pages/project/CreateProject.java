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

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Alignment;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Unit;
import org.apache.wicket.extensions.markup.html.tree.table.IColumn;
import org.apache.wicket.extensions.markup.html.tree.table.PropertyTreeColumn;
import org.apache.wicket.extensions.markup.html.tree.table.TreeTable;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.persistence.provider.ProjectXstreamDao;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.jabox.application.ICreateProjectUtil;
import org.jabox.model.MavenArchetype;
import org.jabox.model.Project;
import org.jabox.webapp.pages.BaseProjectsPage;
import org.jabox.webapp.validation.ShinyForm;

import com.google.inject.Inject;

@AuthorizeInstantiation("ADMIN")
public class CreateProject extends BaseProjectsPage {
    private static final long serialVersionUID = -6051173629887691918L;

    @Inject
    private ICreateProjectUtil _createProjectUtil;

    private TreeTable _tree;

    public CreateProject() {
        final Project _project = new Project();
        MavenArchetype ma =
            new MavenArchetype("org.apache.maven.archetypes",
                "maven-archetype-webapp", "LATEST");
        _project.setMavenArchetype(ma);

        // Add a form with an onSumbit implementation that sets a message
        Form<Project> form = new ShinyForm<Project>("form") {
            private static final long serialVersionUID =
                -662744155604166387L;

            @Override
            protected void onSubmit() {
                if (_tree.getTreeState().getSelectedNodes().size() == 0) {
                    error("Select an archetype first.");
                    return;
                }
                // Pass the MavenArchetype from TreeMap to Project
                _project
                    .setMavenArchetype((MavenArchetype) ((DefaultMutableTreeNode) _tree
                        .getTreeState().getSelectedNodes().toArray()[0])
                        .getUserObject());

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

        List<MavenArchetype> connectors = new ArrayList<MavenArchetype>();
        connectors.add(ma);
        connectors = fillArchetypes(connectors);
        System.out.println("connectors: " + ":" + connectors);

        form.add(new TextField<Project>("sourceEncoding"));
        form.add(new CheckBox("signArtifactReleases"));

        IColumn columns[] =
            new IColumn[] {
                    new PropertyTreeColumn<String>(new ColumnLocation(
                        Alignment.LEFT, 30, Unit.EM), "groupId",
                        "userObject.archetypeGroupId"),
                    new PropertyTreeColumn<String>(new ColumnLocation(
                        Alignment.LEFT, 30, Unit.EM), "artifactId",
                        "userObject.archetypeArtifactId"),
                    new PropertyTreeColumn<String>(new ColumnLocation(
                        Alignment.LEFT, 20, Unit.EM), "version",
                        "userObject.archetypeVersion"), };

        _tree =
            new TreeTable("treeTable", convertToTreeModel(connectors),
                columns);
        _tree.getTreeState().setAllowSelectMultiple(false);
        _tree.setRootLess(true);
        form.add(_tree);
        _tree.getTreeState().expandAll();
    }

    private List<MavenArchetype> fillArchetypes(
            final List<MavenArchetype> connectors) {
        InputStream is =
            CreateProject.class.getResourceAsStream("archetypes.csv");
        BufferedReader reader =
            new BufferedReader(new InputStreamReader(is));
        String thisLine;
        try {
            while ((thisLine = reader.readLine()) != null) {
                connectors.add(new MavenArchetype(thisLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connectors;
    }

    public static TreeModel convertToTreeModel(
            final List<MavenArchetype> connectors) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
        addToTreeModel(rootNode, connectors);
        TreeModel model = new DefaultTreeModel(rootNode);
        return model;
    }

    private static void addToTreeModel(
            final DefaultMutableTreeNode parent,
            final List<MavenArchetype> connectors) {
        for (MavenArchetype obj : connectors) {
            parent.add(new DefaultMutableTreeNode(obj));
        }
    }
}
