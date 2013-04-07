package org.jabox.scm.gitlab;

import static org.junit.Assert.fail;

import org.jabox.utils.Timestamp;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Fill GITLAB_URL and PRIVATE_TOKEN for integration testing.
 */
@Ignore
public class GitlabFacadeTest {

    private static final String GITLAB_URL = "http://gitlab.example.org";

    private static final String PRIVATE_TOKEN = "myPrivateToken";

    /**
     * Test method for
     * {@link org.jabox.scm.gitlab.GitlabFacade#validateLogin(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testValidateLoginOK() {
        Assert.assertTrue(GitlabFacade.validateLogin(GITLAB_URL,
            PRIVATE_TOKEN));
    }

    @Test
    public void testValidateLoginKO() {
        Assert.assertFalse(GitlabFacade.validateLogin(GITLAB_URL,
            "WrongToken"));
    }

    /**
     * Test method for
     * {@link org.jabox.scm.gitlab.GitlabFacade#createRepowithApi(java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testCreateRepowithApi() {
        GitlabFacade.createRepowithApi(GITLAB_URL, PRIVATE_TOKEN, "repo"
            + Timestamp.now());
    }

    /**
     * Test method for
     * {@link org.jabox.scm.gitlab.GitlabFacade#remoteAddOrigin(java.lang.String, java.lang.String, java.io.File)}
     * .
     */
    public void testRemoteAddOrigin() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link org.jabox.scm.gitlab.GitlabFacade#pushOriginMaster(java.io.File)}.
     */
    public void testPushOriginMaster() {
        fail("Not yet implemented");
    }
}
