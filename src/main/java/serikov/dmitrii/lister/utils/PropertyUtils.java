package serikov.dmitrii.lister.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

public class PropertyUtils {

	private static final XLogger logger = XLoggerFactory.getXLogger(PropertyUtils.class);

	public static final String APP_PROPERTIES_RESOURCE = "/WEB-INF/classes/app.properties";

	private static Map<String, String> propertiesStore = new HashMap<>();

//	public static void loadAppProperties(ServletContext context) {
//		try (InputStream inputStream = context.getResourceAsStream(PropertyUtils.APP_PROPERTIES_RESOURCE)) {
//			if (inputStream != null) {
//				Properties prop = new Properties();
//				prop.load(inputStream);
//				prop.forEach((key, value) -> propertiesStore.put(key.toString(), value.toString()));
//			} else {
//				logger.error(String.format("Cannot find \"%s\"", APP_PROPERTIES_RESOURCE));
//			}
//		} catch (IOException e) {
//		}
//	}

	public static Map<String, String> getAppProperties() {
		return propertiesStore;
	}

}
