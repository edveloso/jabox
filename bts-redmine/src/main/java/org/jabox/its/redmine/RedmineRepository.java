/*
 * Jabox Open Source Version
 * Copyright (C) 2009-2010 Dimitris Kapanidis                                                                                                                          
 * 
 * This file is part of Jabox
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package org.jabox.its.redmine;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.Cookie;

import net.sourceforge.jwebunit.junit.WebTester;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.jabox.apis.its.ITSConnector;
import org.jabox.apis.scm.SCMConnectorConfig;
import org.jabox.model.DeployerConfig;
import org.jabox.model.Project;
import org.jabox.model.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Version;

public class RedmineRepository implements
        ITSConnector<RedmineRepositoryConfig>, Serializable {
    private static final Logger LOGGER = LoggerFactory
        .getLogger(RedmineRepository.class);

    private static final long serialVersionUID = -692328636804684690L;

    public static final String ID = "plugin.its.redmine";

    private final WebConversation _wc = new WebConversation();

    private final WebTester _wt = new WebTester();

    private RedmineManager _mgr;

    private com.taskadapter.redmineapi.bean.Project _redmineProject;

    public String getName() {
        return "Redmine";
    }

    public String getId() {
        return ID;
    }

    @Override
    public String toString() {
        return getName();
    }

    public boolean addModule(final Project project,
            final RedmineRepositoryConfig itsConnectorConfig,
            final String module, final String description,
            final String initialOwner) throws SAXException, IOException {
        return true;
    }

    public boolean addProject(final Project project,
            final RedmineRepositoryConfig config)
            throws IOException, SAXException {
        LOGGER.info("Redmine add Project: " + project.getName());
        _redmineProject = new com.taskadapter.redmineapi.bean.Project();
        _redmineProject.setName(project.getName());
        _redmineProject.setIdentifier(getRedmineId(project));
        _redmineProject.setDescription(project.getDescription());

        try {
            _redmineProject = _mgr.createProject(_redmineProject);
            return true;
        } catch (RedmineException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getRedmineId(final Project project) {
        return project.getName();
    }

    public boolean addVersion(final Project project,
            final RedmineRepositoryConfig config, final String version)
            throws IOException, SAXException {
        LOGGER.info("Redmine add Version: " + version);
        Version ver = new Version(_redmineProject, version);
        try {
            _mgr.createVersion(ver);
            return true;
        } catch (RedmineException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(final RedmineRepositoryConfig config)
            throws MalformedURLException, IOException, SAXException {
        LOGGER.info("Redmine Login: " + config.getUsername());
        _mgr = new RedmineManager(config.getServer().getUrl());
        _mgr.setLogin(config.getUsername());
        _mgr.setPassword(config.getPassword());

        String url = config.getServer().getUrl();
        return login(url, config.getUsername(), config.getPassword());
    }

    protected boolean login(final String url, final String username,
            final String password)
            throws MalformedURLException, IOException, SAXException {
        _wt.setBaseUrl(url);
        _wt.beginAt("/login");
        _wt.setTextField("username", username);
        _wt.setTextField("password", password);
        _wt.submit();

        if (_wt.getDialog().getPageURL().toExternalForm()
            .endsWith("/my/page")) {
            return true;
        } else {
            return false;
        }
    }

    public DeployerConfig newConfig() {
        return new RedmineRepositoryConfig();
    }

    public Component newEditor(final String id, final IModel<Server> model) {
        return new RedmineRepositoryEditor(id, model);
    }

    public void addRepository(final Project project,
            final RedmineRepositoryConfig config,
            final SCMConnectorConfig scmConfig, final String username,
            final String password)
            throws MalformedURLException, IOException, SAXException {
        login(config);
        LOGGER.info("Redmine add Repository: " + scmConfig.getScmUrl());

        // Check if Repository should be added
        if (!config.isAddRepositoryConfiguration()) {
            LOGGER.debug("Redmine Repository config is disabled.");
            return;
        }

        List<Cookie> cookies = (List<Cookie>) _wt.getDialog().getCookies();
        for (Cookie cookie : cookies) {
            _wc.putCookie(cookie.getName(), cookie.getValue());
        }
        String token = getAuthenticityToken(_wt.getPageSource());
        LOGGER.debug("Token: {}", token);

        String scm = getScmType(scmConfig);
        LOGGER.debug("SCM CONFIG: {}", scm);

        postData(config, project, scmConfig, username, password, scm);
    }

    /**
     * @throws SAXException
     * @throws IOException
     * @throws MalformedURLException
     */
    private void postData(final RedmineRepositoryConfig config,
            final Project project, final SCMConnectorConfig scmConfig,
            final String username, final String password, final String scm) {
        LOGGER.info("Redmine add Repository: " + scmConfig.getScmUrl());

        // Check if Repository should be added
        if (!config.isAddRepositoryConfiguration()) {
            LOGGER.debug("Repository config is disabled");
            return;
        }

        List<Cookie> cookies = (List<Cookie>) _wt.getDialog().getCookies();
        for (Cookie cookie : cookies) {
            LOGGER.debug("Cookie: {}", cookie);
            _wc.putCookie(cookie.getName(), cookie.getValue());
        }

        PostMethodWebRequest form =
            new PostMethodWebRequest(config.getServer().getUrl()
                + "/projects/" + project.getName() + "/repositories");
        form.setParameter("authenticity_token",
            getAuthenticityToken(_wt.getPageSource()));
        form.setParameter("repository_scm", scm);
        form.setParameter("repository[url]",
            scmConfig.getProjectScmUrl(project.getName()));
        form.setParameter("repository[login]", username);
        form.setParameter("repository[password]", password);
        form.setParameter("commit", "Create");
        try {
            LOGGER.debug("Posting: {}", form);
            _wc.getResponse(form);
            LOGGER.debug("Posted");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Returns the Redmine SCM value of the dropdown in Repository Settings.
     * 
     * @param scmConfig
     *            the SCM configuration
     * @return the Redmine SCM value of the dropdown in Repository Settings
     */
    private String getScmType(final SCMConnectorConfig scmConfig) {
        if (scmConfig.getScmUrl().startsWith("git")) {
            return "Git";
        } else {
            return "Subversion";
        }
    }

    protected static String getAuthenticityToken(final String body) {
        String sub[] = body.split("meta content=\"");
        String token = sub[2].split("\"")[0];
        LOGGER.debug("Token: {}", token);
        return token;
    }

}
