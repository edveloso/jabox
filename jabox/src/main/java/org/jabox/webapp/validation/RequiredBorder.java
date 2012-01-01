package org.jabox.webapp.validation;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.border.MarkupComponentBorder;
import org.apache.wicket.markup.html.form.FormComponent;

public class RequiredBorder extends MarkupComponentBorder {
	public void renderAfter(Component component) {
		FormComponent fc = (FormComponent) component;
		if (fc.isRequired()) {
			super.afterRender(component);
		}
	}
}
