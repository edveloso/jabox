package org.jabox.scm.github;

import java.io.File;
import java.io.IOException;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.jabox.scm.git.Executor;

public class GitHubFacade {

    /**
     * Validates login on GitHub.
     * 
     * @param username
     * @param token
     * @return true if username & token are valid, false otherwise.
     */
    public static boolean validateLogin(final String username,
            final String token) {
        GitHubClient client = new GitHubClient();
        client.setCredentials(username, token);

        RepositoryService service = new RepositoryService(client);
        try {
            service.getRepositories();
        } catch (IOException e1) {
            return false;
        }
        return true;
    }

    /**
     * @param username
     * @param token
     * @param repository
     *            The repository name
     * @return true if the Repository was created, false otherwise
     */
    public static boolean createRepowithApi(final String username,
            final String token, final String repository) {
        GitHubClient client = new GitHubClient();
        client.setCredentials(username, token);

        RepositoryService service = new RepositoryService(client);

        Repository repo = new Repository();
        repo.setName(repository);
        try {
            service.createRepository(repo);
        } catch (IOException e) {
            return false;
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
