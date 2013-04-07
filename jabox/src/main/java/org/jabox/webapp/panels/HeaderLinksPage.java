/*
 * Jabox Open Source Version
 * Copyright (C) 2009-2010 Dimitris Kapanidis                                                                                                                          
 * 
 * This file is part of Jabox
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package org.jabox.webapp.panels;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.jabox.webapp.pages.TabPage;

/**
 * {@link HeaderLinksPage} is showing the current C.I.S. inside an
 * <code>iframe</code>. TopMenu is visible in order to navigate from one server
 * to another easily.
 */
public class HeaderLinksPage extends TabPage {
    private static final long serialVersionUID = 1152914881732787560L;

    public HeaderLinksPage(PageParameters params) {
        StringValue selected = params.get("selected");
        add(new FrameHeaderLinksPanel("headerLinks", selected.toInt()));
    }
}
