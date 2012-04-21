package org.jabox.webapp.borders;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.FormComponent;

public class ValidationMsgBehavior extends Behavior {
    /**
     * @see org.apache.wicket.behavior.Behavior#afterRender(org.apache.wicket.Component)
     */
    @Override
    public void afterRender(final Component c) {
        if (FormComponent.class.isInstance(c)) {
            FormComponent fc = (FormComponent) c;
            if (!fc.isValid()) {
                String error;
                if (fc.hasFeedbackMessage()) {
                    error =
                        fc.getFeedbackMessage().getMessage().toString();
                } else {
                    error = "Your input is invalid.";
                }
                fc.getResponse().write(
                    "<span class=\"help-inline \">" + error + "</span>");
            }
        }
    }
}
