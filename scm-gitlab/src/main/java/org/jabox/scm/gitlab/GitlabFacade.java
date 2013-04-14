package org.jabox.scm.gitlab;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jabox.scm.git.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitlabFacade {
    private static final Logger LOGGER = LoggerFactory
        .getLogger(GitlabFacade.class);

    private static final String API = "/api/v3";

    /**
     * Validates login on Gitlab.
     * 
     * @param username
     * @param token
     * @return true if username & token are valid, false otherwise.
     */
    public static boolean validateLogin(final String url,
            final String token) {
        try {
            String string = url + API + "/projects?private_token=" + token;
            int result =
                new HttpClient().executeMethod(new GetMethod(string));
            if (result == 200) {
                return true;
            }
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * @param username
     * @param token
     * @param repository
     *            The repository name
     * @return true if the Repository was created, false otherwise
     */
    public static boolean createRepowithApi(final String scmUrl,
            final String token, final String repository) {
        return createRepo(scmUrl, token, repository);
    }

    private static boolean createRepo(final String scmUrl,
            final String password, final String repository) {
        HttpClient client = new HttpClient();
        String uri = scmUrl + API + "/projects";
        LOGGER.debug("Create Repo: {}", uri);
        PostMethod post = new PostMethod(uri);

        post.setParameter("name", repository);
        post.setParameter("private_token", password);
        try {
            int result = client.executeMethod(post);
            LOGGER.info("Return code: " + result);
            for (Header header : post.getResponseHeaders()) {
                LOGGER.info(header.toString().trim());
            }
            LOGGER.info(post.getResponseBodyAsString());
        } catch (HttpException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            post.releaseConnection();
        }
        return true;
    }

    public static void remoteAddOrigin(final String scmUrl,
            final String username, final String projectName, final File dir) {
        Executor.exec("git remote add origin " + scmUrl + "/"
            + projectName + ".git", null, dir);
    }

    public static void pushOriginMaster(final File dir) {
        Executor.exec("git push -u origin master", null, dir);
    }
}
