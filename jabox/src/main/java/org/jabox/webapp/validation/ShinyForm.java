package org.jabox.webapp.validation;

import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisitor;

public class ShinyForm<T> extends StatelessForm<T> {

    public ShinyForm(final String id) {
        super(id);
    }

    public ShinyForm(final String id, final IModel<T> model) {
        super(id, model);
    }

    private static final long serialVersionUID = -6795104850360897198L;

    IVisitor shinyVisitor = new ShinyFormVisitor();

    @Override
    public void onBeforeRender() {
        super.onBeforeRender();
        visitChildren(shinyVisitor);
    }
}
