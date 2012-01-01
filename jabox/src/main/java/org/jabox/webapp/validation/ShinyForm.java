package org.jabox.webapp.validation;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisitor;

public class ShinyForm<T> extends Form<T> {

	public ShinyForm(String id) {
		super(id);
	}

	public ShinyForm(String id, IModel<T> model) {
		super(id, model);
	}

	private static final long serialVersionUID = -6795104850360897198L;
	IVisitor shinyVisitor = new ShinyFormVisitor();

	public void onBeforeRender() {
		super.onBeforeRender();
		visitChildren(shinyVisitor);
	}
}
