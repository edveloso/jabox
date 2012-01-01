package org.jabox.webapp.validation;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;

public class ErrorHighlightBehavior extends AbstractBehavior {
	public void onComponentTag(Component c, ComponentTag tag) {
		FormComponent fc = (FormComponent) c;
		if (!fc.isValid()) {
			tag.append("class", " ", " error");
		}
	}
}
