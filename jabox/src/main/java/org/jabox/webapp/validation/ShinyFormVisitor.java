package org.jabox.webapp.validation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.jabox.webapp.borders.ControlGroupBehavior;
import org.jabox.webapp.borders.ControlsBehavior;
import org.jabox.webapp.borders.ErrorHighlightBehavior;
import org.jabox.webapp.borders.HelpBehavior;
import org.jabox.webapp.borders.LabelBehavior;
import org.jabox.webapp.borders.ValidationMsgBehavior;

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
            c.add(new HelpBehavior());
            c.add(new ControlGroupBehavior());
            c.add(new LabelBehavior());
            c.add(new ControlsBehavior());
        }
    }
}
