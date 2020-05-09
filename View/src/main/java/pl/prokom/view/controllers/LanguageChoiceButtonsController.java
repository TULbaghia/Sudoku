package pl.prokom.view.controllers;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.view.stage.StageCreator;

public class LanguageChoiceButtonsController {

    private static final Logger logger =
            LoggerFactory.getLogger(LanguageChoiceButtonsController.class);

    private static String clickedToggleID = null;
    /**
     * ToggleButtons responsible for changing theme language.
     */
    @FXML
    RadioMenuItem rmiPolish;
    @FXML
    RadioMenuItem rmiEnglish;
    @FXML
    ToggleGroup langChoiceGroup;

    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    MainPaneWindowController mainController;

    private ResourceBundle bundle = ResourceBundle.getBundle("bundles.interaction");

    /**
     * Initialization method that set-up necessary listeners.
     */
    @FXML
    public void initialize() {
        langChoiceGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioMenuItem newButton = (RadioMenuItem) newToggle;
                logger.info("Changed id from {} to {}", clickedToggleID, newButton.getId());
                clickedToggleID = newButton.getId();
            }
        });
    }

    /**
     * Setting parent controller MainPaneWindowController type to LanguageChoiceButtonsController.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
        langChoiceGroup.getToggles().stream()
                .map(x -> (RadioMenuItem) x)
                .filter(x -> x.getId().equals(clickedToggleID))
                .findFirst()
                .ifPresentOrElse(x -> {
                    x.setSelected(true);
                    x.setDisable(true);
                    logger.info("{} was selected and disabled", clickedToggleID);
                }, () -> {
                    rmiPolish.setSelected(true);
                    rmiPolish.setDisable(true);
                    logger.info("{} was selected and disabled", rmiPolish.getId());
                });
    }

    /**
     * Changing overall interface language to Polish.
     */
    @FXML
    public void setLanguagePolish() throws IOException {
        logger.trace("Changeing language to polish");
        Locale.setDefault(new Locale("pl"));
        StageCreator.createStage(extractStage(), bundle.getBundle("bundles.interaction",
                Locale.getDefault()), this.getClass());
        logger.trace("Changed language to polish");
    }

    /**
     * Changing overall interface language to English.
     */
    @FXML
    public void setLanguageEnglish() throws IOException {
        logger.trace("Changeing language to english");
        Locale.setDefault(new Locale("en"));
        StageCreator.createStage(extractStage(), bundle.getBundle("bundles.interaction",
                Locale.getDefault()), this.getClass());
        logger.trace("Changed language to english");
    }

    public Stage extractStage() {
        return (Stage) this.mainController.getMainPaneWindow().getScene().getWindow();
    }


}
