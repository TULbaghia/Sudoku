package pl.prokom.view.menu;

import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.view.bundles.BundleHelper;

public class AlertBox {

    private static final Logger logger =
            LoggerFactory.getLogger(AlertBox.class);

    /**
     * Displays alert created using given parameters.
     *
     * @param type    one of AlertType
     * @param title   title of the window
     * @param header  header message
     * @param content content insie window.
     */
    public static void showAlert(
            Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        if (title != null) {
            alert.setContentText(title);
        }
        if (header != null) {
            alert.setHeaderText(header);
        }
        if (content != null) {
            Text text = new Text("\n" + content);
            text.setWrappingWidth(400);
            alert.getDialogPane().setStyle("-fx-padding: 15px;");
            alert.getDialogPane().setContent(text);
        }
        logger.debug(BundleHelper.getApplication("alertComposer"), type, title, header, content);
        alert.showAndWait().ifPresentOrElse(x -> {
            logger.debug(BundleHelper.getApplication("alertClosedWithButton"), x.getText());
        }, () -> {
            logger.debug(BundleHelper.getApplication("alertClosedWithoutButton"));
        });
    }
}
