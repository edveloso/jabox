package org.jabox.cis.jenkins;

import junit.framework.TestCase;

public class JenkinsServerTest extends TestCase {

	public void testStripVersion() {
		String name = JenkinsServer.stripVersion("collector-server-1.1.14.hpi");
		assertEquals("collector-server.hpi", name);
	}
}
