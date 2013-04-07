package org.jabox.webapp.pages.project;

import org.apache.wicket.persistence.provider.ConfigXstreamDao;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.jabox.model.DefaultConfiguration;
import org.jabox.model.Project;

public class CreateProjectValidator implements IValidator<String> {
    private static final long serialVersionUID = 5966531322592486651L;

    public void validate(IValidatable<String> validatable) {
        // get input from attached component
        final String name = validatable.getValue();
        final DefaultConfiguration dc = ConfigXstreamDao.getConfig();

        if (dc.getScm() == null) {
            error(validatable, "scm-null");
        }
        if (dc.getCis() == null) {
            error(validatable, "cis-null");
        }
    }

    private void error(IValidatable<String> validatable, String errorKey) {
        ValidationError error = new ValidationError();
        error.addMessageKey(getClass().getSimpleName() + "." + errorKey);
        validatable.error(error);
    }

}
