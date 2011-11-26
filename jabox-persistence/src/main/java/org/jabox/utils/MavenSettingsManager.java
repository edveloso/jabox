package org.jabox.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.persistence.provider.ConfigXstreamDao;
import org.codehaus.cargo.generic.configuration.DefaultConfigurationFactory;
import org.jabox.environment.Environment;
import org.jabox.model.DefaultConfiguration;

public class MavenSettingsManager {

	public static void writeCustomSettings() {
		File m2Dir = Environment.getCustomMavenHomeDir();
		File file = new File(m2Dir, "settings.xml");
		if (!file.exists()) {
			try {
				writeCustomSettings(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void writeCustomSettings(final File file) throws IOException {
		DefaultConfiguration dc = ConfigXstreamDao.getConfig();
		
		InputStream is = MavenSettingsManager.class.getResourceAsStream("settings.xml");

		Map<String, String> values = new HashMap<String, String>();
		values.put("${repo.url}", "http://localhost:8080/nexus/content/groups/public/");
		values.put("${repo.username}", "admin");
		values.put("${repo.password}", "admin123");
		
		String data = SettingsModifier.parseInputStream(is, values);
		
		FileUtils.writeStringToFile(file, data);
	}

}
