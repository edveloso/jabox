package org.jabox.scm.gitlab;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jabox.scm.git.Executor;

public class GitlabFacade {

    private static final String LOGIN_REGEX =
        "http://github.com/api/v2/yaml/user/show?login=%s&token=%s";

    /**
     * Validates login on Gitlab.
     * 
     * @param username
     * @param token
     * @return true if username & token are valid, false otherwise.
     */
    public boolean validateLogin(final String username, final String token) {
        String uri = String.format(LOGIN_REGEX, username, token);
        try {
            int result =
                new HttpClient().executeMethod(new GetMethod(uri));
            System.out.println(result);
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
        String uri = scmUrl + "/api/v3/projects";
        PostMethod post = new PostMethod(uri);

        post.setParameter("name", repository);
        post.setParameter("private_token", password);
        try {
            int result = client.executeMethod(post);
            System.out.println("Return code: " + result);
            for (Header header : post.getResponseHeaders()) {
                System.out.println(header.toString().trim());
            }
            System.out.println(post.getResponseBodyAsString());
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

    public static void remoteAddOrigin(final String username,
            final String projectName, final File dir) {
        Executor.exec("git remote add origin git@github.com:" + username
            + "/" + projectName + ".git", null, dir);
    }

    public static void pushOriginMaster(final File dir) {
        Executor.exec("git push origin master", null, dir);
    }
}
