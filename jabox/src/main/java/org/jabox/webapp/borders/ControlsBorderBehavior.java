package org.jabox.webapp.borders;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.border.BorderBehavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.StringResourceModel;

/**
 * $Id$
 * @author dimitris
 * @date   Apr 20, 2012 8:23:15 PM
 *
 * Copyright (C) 2012 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */

/**
 *
 */
public class ControlsBorderBehavior extends BorderBehavior {
    private static final long serialVersionUID = -6251370873692995058L;

    @Override
    public void afterRender(final Component c) {
        if (FormComponent.class.isInstance(c)) {
            String name = c.getClass().getName();
            System.out.println(name);
            StringResourceModel tooltip =
                new StringResourceModel(c.getId() + ".tooltip",
                    c.getPage(), null);
            c.getResponse().write(
                "<p class=\"help-block\">" + tooltip.getObject() + "</p>");
        }
        super.afterRender(c);
    }
}
