package org.jabox.webapp.borders;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.border.MarkupComponentBorder;
import org.apache.wicket.markup.html.form.FormComponent;

public class RequiredBorder extends MarkupComponentBorder {
    public void renderAfter(final Component c) {
        FormComponent fc = (FormComponent) c;
        if (fc.isRequired()) {
            if ("picker".equals(c.getId())) {
                return;
            }

            super.afterRender(c);
        }
    }
}
