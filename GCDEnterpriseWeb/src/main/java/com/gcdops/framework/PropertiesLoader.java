package com.gcdops.framework;

import java.net.URL;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton class to enable properties objects to be loaded from files stored
 * in the class path.
 * @author rohit.sharma
 */
public final class PropertiesLoader {
	
	/**
	 * Logger defined to log messages
	 */
	private static final GCDOpsLogger LOGGER = GCDOpsLogger.getInstance(GCDOpsRegistry.class);
	
	/**
	 * Singleton instance of the class.
	 */
	private static final PropertiesLoader INSTANCE = new PropertiesLoader();

	/**
	 * Creates a new instance of PropertiesLoader.
	 */
	private PropertiesLoader() {
	}

	/**
	 * Return the singleton instance of this class.
	 *
	 * @return Instance of the singleton
	 */
	public static PropertiesLoader getInstance() {
		return INSTANCE;
	}

	/**
	 * Load up the specified properties file from the class path.
	 *
	 * @param fileName filename of the properties file to be loaded
	 * @throws IOException thrown if there were problems with the file
	 * @return Properties object containing the contents of the file
	 */
	public Properties loadPropertiesFromClassPath(final String fileName) throws IOException {
		
		LOGGER.debug("Attempting to load properties file " + fileName);
		// Lookup my class loader
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource(fileName);
		if (url != null) {
			LOGGER.debug("File located at " + url);
			Properties props = new Properties();
			props.load(url.openStream());
			return props;
		} else {
			LOGGER.warn("No properties file found: " + fileName);
			return null;
		}
	}
}
