package org.jabox.webapp.borders;

import org.apache.wicket.markup.html.border.Border;
import org.jabox.webapp.panels.HeaderLinksPanel;

public class ContainersBorder extends Border {
    private static final long serialVersionUID = -6762014978866138140L;

    private final HeaderLinksPanel headers;

    public ContainersBorder() {
        this(1);
    }

    public ContainersBorder(final int menuIndex) {
        super("border");
        headers = new HeaderLinksPanel();
        addToBorder(headers);
    }
}
