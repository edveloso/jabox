package org.jabox.scm.gitlab;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.UrlValidator;

public class GitlabLoginValidator extends UrlValidator {
    private static final long serialVersionUID = 8702375585446955943L;

    private final TextField<String> _url;

    private final PasswordTextField _apitoken;

    public GitlabLoginValidator(final TextField<String> url,
            final PasswordTextField password) {
        _url = url;
        _apitoken = password;
    }

    @Override
    protected void onValidate(final IValidatable<String> validatable) {
        if (!_url.isValid() || !_apitoken.isValid()) {
            return;
        }

        boolean login =
            GitlabFacade.validateLogin(_url.getValue(),
                _apitoken.getValue());
        if (!login) {
            error(_url.newValidatable());
        }
    }
}
