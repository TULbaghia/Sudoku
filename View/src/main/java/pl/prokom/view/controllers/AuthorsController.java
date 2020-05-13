package pl.prokom.view.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.view.bundles.BundleHelper;
import pl.prokom.view.menu.AlertBox;

public class AuthorsController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorsController.class);

    private MainPaneWindowController mainPaneWindowController;

    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        logger.trace(BundleHelper.getApplication("initializingParentController"));
        this.mainPaneWindowController = mainPaneWindowController;
    }

    @FXML
    private void openAuthorBox(Event actionEvent) {
        logger.trace(BundleHelper.getApplication("authorsLoadBundle"));

        showAlert(BundleHelper.getAuthors("label"), buildAuthorsString());
    }

    private String buildAuthorsString() {
        logger.trace(BundleHelper.getApplication("authorsBuildingString"));
        StringBuilder authors = new StringBuilder();

        try {
            int numer = Integer.parseInt(BundleHelper.getAuthors("authorNumber"));

            for (int i = 1; i <= numer; i++) {
                authors.append(BundleHelper.getAuthors("author_" + i));
                if (i != numer) {
                    authors.append("\n");
                }
            }
        } catch (NumberFormatException e) {
            logger.error(BundleHelper.getException("error") + " - "
                    + BundleHelper.getException("numberFormatException"), e);
        }

        return authors.toString();
    }

    private void showAlert(String label, String content) {
        logger.debug(BundleHelper.getApplication("authorsAlertShowing"));

        AlertBox.showAlert(AlertType.INFORMATION, label, label + ":", content);

        logger.debug(BundleHelper.getApplication("authorsAlertClosed"));
    }
}
