package pl.prokom.view.bundles;

import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton that reads data from Exception ResourceBundle.
 */
public class BundleHelper {

    private static final Logger logger =
            LoggerFactory.getLogger(BundleHelper.class);

    private static String bundleHandler(ResourceBundle resourceBundle, String message) {
        if (resourceBundle.containsKey(message)) {
            return resourceBundle.getString(message);
        } else {
            logger.warn(BundleHelper.getException("warn") + " - "
                    + BundleHelper.getException("missingResourceBundleName"), message);
            return message;
        }
    }

    public static String getException(String message) {
        return bundleHandler(ResourceBundle.getBundle("bundles.exception"), message);
    }

    public static String getApplication(String message) {
        return bundleHandler(ResourceBundle.getBundle("bundles.application"), message);
    }

    public static String getInteraction(String message) {
        return bundleHandler(ResourceBundle.getBundle("bundles.interaction"), message);
    }

    public static String getAuthors(String message) {
        return bundleHandler(ResourceBundle.getBundle("pl.prokom.view.bundles.Authors"), message);
    }

}
