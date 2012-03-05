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
package org.jabox.webapp.pages.container;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.jabox.apis.embedded.EmbeddedServer;
import org.jabox.cis.hudson.HudsonServer;
import org.jabox.its.jtrac.JtracServer;
import org.jabox.model.Container;
import org.jabox.mrm.artifactory.ArtifactoryServer;
import org.jabox.webapp.modifiers.TooltipModifier;
import org.jabox.webapp.pages.BasePage;
import org.jabox.webapp.validation.ShinyForm;

public abstract class EditContainerPage extends BasePage {

	public EditContainerPage(final IModel<Container> user) {
		Form<Container> form = new ShinyForm<Container>("form",
				new CompoundPropertyModel<Container>(user.getObject())) {
			private static final long serialVersionUID = -8262391690702864764L;

			@Override
			protected void onSubmit() {
				onSave(getModelObject());
			}
		};

		add(form);

		form.add(new RequiredTextField<Container>("name"));
		form.add(new RequiredTextField<Container>("port"));
		form.add(new RequiredTextField<Container>("rmiPort"));
		form.add(new RequiredTextField<Container>("ajpPort"));
		form.add(new RequiredTextField<Container>("jvmArgs"));

		List<EmbeddedServer> webapps = new ArrayList<EmbeddedServer>();
		webapps.addAll(user.getObject().getServers());
		webapps.add(new ArtifactoryServer());
		webapps.add(new HudsonServer());
		webapps.add(new JtracServer());
		form.add(new CheckBoxMultipleChoice<EmbeddedServer>("webapps", webapps));
	}

	protected abstract void onSave(Container container);

	protected abstract void onCancel();
}
