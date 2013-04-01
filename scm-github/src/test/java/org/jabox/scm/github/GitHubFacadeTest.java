package org.jabox.scm.github;

import org.jabox.utils.Timestamp;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class GitHubFacadeTest {

    private static final String USER = "user";

    private static final String PWD = "pwd";

    @Test
    public void testLoginOK() {
        Assert.assertTrue(GitHubFacade.validateLogin(USER, PWD));
    }

    @Test
    public void testLoginKO() {
        Assert.assertFalse(GitHubFacade.validateLogin(USER, "wrongPwd"));
    }

    /**
     * Test method for
     * {@link org.jabox.scm.gitlab.GitlabFacade#createRepowithApi(java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testCreateRepowithApi() {
        GitHubFacade
            .createRepowithApi(USER, PWD, "repo" + Timestamp.now());
    }

    // public void testPush() {
    // File dir = new File("target/git");
    // GitHubFacade.pushOriginMaster(dir);
    // }
}
