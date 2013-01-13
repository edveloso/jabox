package org.jabox.scm.gitlab;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.UrlValidator;

public class GitlabLoginValidator extends UrlValidator {
    private static final long serialVersionUID = 8702375585446955943L;

    private final TextField<String> _username;

    private final PasswordTextField _apitoken;

    public GitlabLoginValidator(final TextField<String> username,
            final PasswordTextField password) {
        _username = username;
        _apitoken = password;
    }

    @Override
    protected void onValidate(final IValidatable<String> validatable) {
        if (!_username.isValid() || !_apitoken.isValid()) {
            return;
        }
        return;

        // boolean login = GitlabFacade.validateLogin(_username.getValue(),
        // _apitoken.getValue());
        // if (!login) {
        // error(_username.newValidatable());
        // }
    }
}
