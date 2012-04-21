package org.jabox.webapp.validation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.jabox.webapp.borders.ControlsBorderBehavior;

public class ShinyFormVisitor implements IVisitor<Component, Object>,
        Serializable {
    private static final long serialVersionUID = -7085121271194228088L;

    Set visited = new HashSet();

    public void component(final Component c, final IVisit<Object> visit) {
        if (!visited.contains(c)) {
            visited.add(c);
            // c.setComponentBorder(new RequiredBorder());
            c.add(new ValidationMsgBehavior());
            c.add(new ErrorHighlightBehavior());
            c.add(new ControlsBorderBehavior());
        }
    }
}
