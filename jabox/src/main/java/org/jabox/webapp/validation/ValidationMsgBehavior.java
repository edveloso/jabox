package org.jabox.webapp.validation;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.form.FormComponent;

public class ValidationMsgBehavior extends AbstractBehavior {
	public void onRendered(Component c) {
		if (FormComponent.class.isInstance(c)) {
			FormComponent fc = (FormComponent) c;
			if (!fc.isValid()) {
				String error;
				if (fc.hasFeedbackMessage()) {
					error = fc.getFeedbackMessage().getMessage().toString();
				} else {
					error = "Your input is invalid.";
				}
				fc.getResponse().write(
						"<div class=\"validationMsg\">" + error + "</div>");
			}
		}
	}
}
