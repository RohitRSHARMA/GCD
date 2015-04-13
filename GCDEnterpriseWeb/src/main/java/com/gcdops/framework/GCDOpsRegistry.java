package com.gcdops.framework;



import java.io.IOException;
import java.util.Properties;

/**
* @author rohit.sharma
*
*/
public class GCDOpsRegistry {
  private static final GCDOpsLogger LOGGER = GCDOpsLogger.getInstance(GCDOpsRegistry.class);

  /**
	 * Variable to hold singleton instance.
	 */
	private static final GCDOpsRegistry INSTANCE = new GCDOpsRegistry();

	/**
	 * Constant for property file name.
	 */
	private static final String FILENAME = "gcdops.properties";

	/**
	 * Variable to hold the property bag.
	 */
	private Properties _tableProperties;

	/**
	 * Private Constructor which initializes the properties from the file.
	 */
	private GCDOpsRegistry() {
		try {
			_tableProperties = PropertiesLoader.getInstance()
					.loadPropertiesFromClassPath(FILENAME);
		} catch (IOException e) {
			LOGGER.error("Unable to load property file.", e);
		}
	}

	/**
	 * Returns singleton instance of TableRegistry.
	 *
	 * @return the instance.
	 */
	public static final GCDOpsRegistry getInstance() {
		return INSTANCE;
	}

	/**
	 * This method returns all file path properties.
	 *
	 * @return All file path properties.
	 */
	public final Properties getAllProperties() {
		return _tableProperties;
	}

	/**
	 * This method returns a property value with the given name.
	 *
	 * @param propertyName The name of the property for which value is required.
	 * @return A property value with the given name.
	 */
	public final String getProperty(final String propertyName) {
		return _tableProperties.getProperty(propertyName);
	}
}
