package org.jabox.webapp.borders;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.FormComponent;

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
public class ControlGroupBehavior extends Behavior {
    private static final long serialVersionUID = -6251370873692995058L;

    /**
     * @see org.apache.wicket.markup.html.border.BorderBehavior#beforeRender(org.apache.wicket.Component)
     */
    @Override
    public void beforeRender(final Component c) {
        if (FormComponent.class.isInstance(c)) {
            c.getResponse().write("<div class=\"control-group\">");
        }
    }

    @Override
    public void afterRender(final Component c) {
        if (FormComponent.class.isInstance(c)) {
            c.getResponse().write("</div> <!--cg -->");
        }
    }
}
