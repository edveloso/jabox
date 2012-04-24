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
package org.jabox.webapp.pages;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.jabox.webapp.panels.JaboxFeedbackPanel;
import org.jabox.webapp.validation.ShinyForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaboxSignInPanel extends Panel {
    private static final long serialVersionUID = 1L;

    /** Log. */
    private static final Logger log = LoggerFactory
        .getLogger(JaboxSignInPanel.class);

    private static final String SIGN_IN_FORM = "signInForm";

    /** True if the panel should display a remember-me checkbox */
    private boolean includeRememberMe = false;

    /** True if the user should be remembered via form persistence (cookies) */
    private boolean rememberMe = true;

    /** password. */
    private String password;

    /** user name. */
    private String username;

    /**
     * @see org.apache.wicket.Component#Component(String)
     */
    public JaboxSignInPanel(final String id) {
        this(id, true);
    }

    /**
     * @param id
     *            See Component constructor
     * @param includeRememberMe
     *            True if form should include a remember-me checkbox
     * @see org.apache.wicket.Component#Component(String)
     */
    public JaboxSignInPanel(final String id,
            final boolean includeRememberMe) {
        super(id);

        this.includeRememberMe = includeRememberMe;

        // Create feedback panel and add to page
        add(new JaboxFeedbackPanel("feedback"));

        // Add sign-in form to page, passing feedback panel as
        // validation error handler
        add(new JaboxSignInForm(SIGN_IN_FORM));
    }

    /**
     * @return signin form
     */
    protected JaboxSignInForm getForm() {
        return (JaboxSignInForm) get(SIGN_IN_FORM);
    }

    /**
     * @see org.apache.wicket.Component#onBeforeRender()
     */
    @Override
    protected void onBeforeRender() {
        // logged in already?
        if (isSignedIn() == false) {
            IAuthenticationStrategy authenticationStrategy =
                getApplication().getSecuritySettings()
                    .getAuthenticationStrategy();
            // get username and password from persistence store
            String[] data = authenticationStrategy.load();

            if ((data != null) && (data.length > 1)) {
                // try to sign in the user
                if (signIn(data[0], data[1])) {
                    username = data[0];
                    password = data[1];

                    // logon successful. Continue to the original destination
                    if (!continueToOriginalDestination()) {
                        // Ups, no original destination. Go to the home page
                        throw new RestartResponseException(
                            getApplication().getSessionSettings()
                                .getPageFactory()
                                .newPage(getApplication().getHomePage()));
                    }
                } else {
                    // the loaded credentials are wrong. erase them.
                    authenticationStrategy.remove();
                }
            }
        }

        // don't forget
        super.onBeforeRender();
    }

    /**
     * Convenience method to access the password.
     * 
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password
     * 
     * @param password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Convenience method to access the username.
     * 
     * @return The user name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username
     * 
     * @param username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Get model object of the rememberMe checkbox
     * 
     * @return True if user should be remembered in the future
     */
    public boolean getRememberMe() {
        return rememberMe;
    }

    /**
     * @param rememberMe
     *            If true, rememberMe will be enabled (username and password
     *            will be persisted somewhere)
     */
    public void setRememberMe(final boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     * Sign in user if possible.
     * 
     * @param username
     *            The username
     * @param password
     *            The password
     * @return True if signin was successful
     */
    private boolean signIn(final String username, final String password) {
        return AuthenticatedWebSession.get().signIn(username, password);
    }

    /**
     * @return true, if signed in
     */
    private boolean isSignedIn() {
        return AuthenticatedWebSession.get().isSignedIn();
    }

    /**
     * Called when sign in failed
     */
    protected void onSignInFailed() {
        // Try the component based localizer first. If not found try the
        // application localizer. Else use the default
        error(getLocalizer().getString("signInFailed", this,
            "Sign in failed"));
    }

    /**
     * Called when sign in was successful
     */
    protected void onSignInSucceeded() {
        // If login has been called because the user was not yet logged in, than
        // continue to the
        // original destination, otherwise to the Home page
        if (!continueToOriginalDestination()) {
            setResponsePage(getApplication().getHomePage());
        }
    }

    /**
     * Sign in form.
     */
    public final class JaboxSignInForm extends ShinyForm<JaboxSignInPanel> {
        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         * 
         * @param id
         *            id of the form component
         */
        public JaboxSignInForm(final String id) {
            super(id);

            setModel(new CompoundPropertyModel<JaboxSignInPanel>(
                JaboxSignInPanel.this));

            // Attach textfields for username and password
            add(new RequiredTextField<String>("username"));
            add(new PasswordTextField("password"));

            // MarkupContainer row for remember me checkbox
            // WebMarkupContainer rememberMeRow =
            // new WebMarkupContainer("rememberMeRow");
            // add(rememberMeRow);

            // Add rememberMe checkbox
            add(new CheckBox("rememberMe"));

            // Show remember me checkbox?
            // rememberMeRow.setVisible(includeRememberMe);
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit() {
            IAuthenticationStrategy strategy =
                getApplication().getSecuritySettings()
                    .getAuthenticationStrategy();

            if (signIn(getUsername(), getPassword())) {
                if (rememberMe == true) {
                    strategy.save(username, password);
                } else {
                    strategy.remove();
                }

                onSignInSucceeded();
            } else {
                onSignInFailed();
                strategy.remove();
            }
        }
    }
}
