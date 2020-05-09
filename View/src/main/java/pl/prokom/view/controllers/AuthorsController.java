package pl.prokom.view.controllers;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorsController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorsController.class);

    private MainPaneWindowController mainPaneWindowController;

    public AuthorsController() {
    }

    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainPaneWindowController = mainPaneWindowController;
    }

    @FXML
    private void openAuthorBox(Event actionEvent) {
        logger.trace("Loading author's resourceBundle");
        ResourceBundle resourceBundle = ResourceBundle
                .getBundle("pl.prokom.view.bundles.Authors", Locale.getDefault());

        showAlert(resourceBundle.getString("label"), buildAuthorsString(resourceBundle));
    }

    private String buildAuthorsString(ResourceBundle resourceBundle) {
        logger.trace("Building author's list");
        int numer = Integer.parseInt(resourceBundle.getString("authorNumber"));

        StringBuilder authors = new StringBuilder();
        for (int i = 1; i <= numer; i++) {
            authors.append(resourceBundle.getString("author_" + i));
            if (i != numer) {
                authors.append("\n");
            }
        }

        return authors.toString();
    }

    private void showAlert(String label, String content) {
        logger.info("Showing author alert");
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle(label);
        alert.setHeaderText(label + ":");
        alert.setContentText(content);

        alert.showAndWait().ifPresent(x -> {
            if(x == ButtonType.OK) {
                logger.info("Pressed OK button");
            }
        });
        logger.info("Closed author alert");
    }
}
