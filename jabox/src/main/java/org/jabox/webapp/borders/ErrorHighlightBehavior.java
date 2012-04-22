package org.jabox.webapp.borders;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;

public class ErrorHighlightBehavior extends AbstractBehavior {
    @Override
    public void onComponentTag(final Component c, final ComponentTag tag) {
        if (FormComponent.class.isInstance(c)) {
            if ("picker".equals(c.getId())) {
                return;
            }
            FormComponent fc = (FormComponent) c;
            if (!fc.isValid()) {
                tag.append("class", " ", " error");
            }
        }
    }
}
