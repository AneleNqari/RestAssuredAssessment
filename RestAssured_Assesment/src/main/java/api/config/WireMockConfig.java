package api.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class WireMockConfig {
	private static Properties properties = new Properties();

	static {
		try (FileInputStream input = new FileInputStream("src/main/resources/wiremock.properties")) {
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load WireMock configuration", e);
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
