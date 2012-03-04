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
package org.jabox.webapp.pages.server;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.jabox.apis.Connector;
import org.jabox.model.Project;
import org.jabox.model.Server;
import org.jabox.webapp.pages.BasePage;
import org.jabox.webapp.pages.DeployerPluginSelector;
import org.jabox.webapp.validation.ShinyForm;

public abstract class EditServerPage extends BasePage {
	private static final long serialVersionUID = -5076249191943115296L;

	public EditServerPage(final IModel<Server> server,
			final Class<? extends Connector> connectorClass) {
		Form<Server> form = new ShinyForm<Server>("form",
				new CompoundPropertyModel<Server>(server.getObject())) {
			private static final long serialVersionUID = -8262391690705860769L;

			@Override
			protected void onSubmit() {
				onSave(getModelObject());
			}
		};

		add(form);

		CompoundPropertyModel<Server> model = new CompoundPropertyModel<Server>(
				server);

		form.add(new RequiredTextField<Project>("name"));

		form.add(new DeployerPluginSelector("configuration", model,
				connectorClass));
	}

	protected abstract void onSave(Server article);

	protected abstract void onCancel();
}
