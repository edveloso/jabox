package org.jabox.cis.jenkins;

import org.jabox.apis.cis.CISConnector;
import org.jabox.cis.ejenkins.EJenkinsConnector;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.multibindings.Multibinder;

public class JenkinsModule implements Module {

    @Override
    public void configure(Binder binder) {
        Multibinder<CISConnector> uriBinder =
            Multibinder.newSetBinder(binder, CISConnector.class);
        uriBinder.addBinding().to(JenkinsConnector.class);
        uriBinder.addBinding().to(EJenkinsConnector.class);

    }
}
