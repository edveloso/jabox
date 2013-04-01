package org.jabox.webapp.borders;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.FormComponent;

public class ControlGroupBehavior extends Behavior {
    private static final long serialVersionUID = -6251370873692995058L;

    /**
     * @see org.apache.wicket.markup.html.border.BorderBehavior#beforeRender(org.apache.wicket.Component)
     */
    @Override
    public void beforeRender(final Component c) {
        if (FormComponent.class.isInstance(c)) {
            if ("picker".equals(c.getId())) {
                return;
            }

            String status = getComponentStatus(c);
            c.getResponse().write(
                "<div class=\"control-group " + status + "\">");
        }
    }

    /**
     * @return
     */
    private String getComponentStatus(final Component c) {
        if (c.getSession().getFeedbackMessages().isEmpty()) {
            return "";
        }

        String result = "";
        if (c.getFeedbackMessage() == null) {
            return "success";
        } else {
            switch (c.getFeedbackMessage().getLevel()) {
            case FeedbackMessage.ERROR:
            case FeedbackMessage.FATAL:
                result = "error";
                break;
            case FeedbackMessage.SUCCESS:
                result = "success";
                break;
            default:
                result = "success";
            }
        }
        return result;
    }

    @Override
    public void afterRender(final Component c) {
        if (FormComponent.class.isInstance(c)) {
            c.getResponse().write("</div> <!--cg -->");
        }
    }
}
