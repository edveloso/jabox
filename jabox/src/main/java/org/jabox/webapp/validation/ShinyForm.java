package org.jabox.webapp.validation;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisitor;
import org.jabox.model.User;

public class ShinyForm extends Form<User> {

	public ShinyForm(String id, IModel model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -6795104850360897198L;
	IVisitor shinyVisitor = new ShinyFormVisitor();

	public void onBeforeRender() {
		super.onBeforeRender();
		visitChildren(shinyVisitor);
	}
}
