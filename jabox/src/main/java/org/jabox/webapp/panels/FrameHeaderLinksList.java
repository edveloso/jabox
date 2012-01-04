package org.jabox.webapp.panels;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.jabox.model.Container;
import org.jabox.webapp.menubuttons.DeleteEntityButton;
import org.jabox.webapp.menubuttons.StartContainerButton;
import org.jabox.webapp.menubuttons.StopContainerButton;
import org.jabox.webapp.pages.container.ManageContainers;
import org.jabox.webapp.utils.EvenOddRow;

public class FrameHeaderLinksList extends PropertyListView<Tab>{
	private static final long serialVersionUID = -2877438240039632971L;

	public FrameHeaderLinksList(final String id, final List<Tab> containers) {
		super(id, containers);
	}

	@Override
	public void populateItem(final ListItem<Tab> listItem) {
		final Tab container = listItem.getModelObject();
//		listItem.add(new Label("name", container.getName()));
//		listItem.add(new Label("port", container.getPort().toString()));
//		final AttributeModifier attributeModifier = new AttributeModifier(
//				"class", true, new EvenOddRow<Container>(listItem));
//		listItem.add(attributeModifier);
//		listItem.add(new StartContainerButton("start", listItem,
//				ManageContainers.class));
//		listItem.add(new StopContainerButton("stop", listItem,
//				ManageContainers.class));
//		listItem.add(new DeleteEntityButton<Container>("delete", listItem,
//				ManageContainers.class));
	}

}
