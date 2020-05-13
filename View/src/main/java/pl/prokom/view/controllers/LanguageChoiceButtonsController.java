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
import pl.prokom.view.bundles.BundleHelper;
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

    /**
     * Initialization method that set-up necessary listeners.
     */
    @FXML
    public void initialize() {
        logger.trace(BundleHelper.getApplication("startingInitialization"));

        langChoiceGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioMenuItem newButton = (RadioMenuItem) newToggle;
                logger.info(BundleHelper.getApplication("languageChoiceGroup"),
                        clickedToggleID, newButton.getId());
                clickedToggleID = newButton.getId();
            }
        });

        logger.trace(BundleHelper.getApplication("finishedInitialization"));
    }

    /**
     * Setting parent controller MainPaneWindowController type to LanguageChoiceButtonsController.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        logger.trace(BundleHelper.getApplication("initializingParentController"));
        this.mainController = mainPaneWindowController;
        langChoiceGroup.getToggles().stream()
                .map(x -> (RadioMenuItem) x)
                .filter(x -> x.getId().equals(clickedToggleID))
                .findFirst()
                .ifPresentOrElse(x -> {
                    x.setSelected(true);
                    x.setDisable(true);
                    logger.info(BundleHelper.getApplication("languageChoiceSelAndDis"),
                            clickedToggleID);
                }, () -> {
                    rmiPolish.setSelected(true);
                    rmiPolish.setDisable(true);
                    logger.info(BundleHelper.getApplication("languageChoiceSelAndDis"),
                            rmiPolish.getId());
                });
    }

    /**
     * Changing overall interface language to Polish.
     */
    @FXML
    public void setLanguagePolish() throws IOException {
        logger.debug(BundleHelper.getApplication("languageChoiceLangChanging"), "polish");
        Locale.setDefault(new Locale("pl"));
        StageCreator.createStage(extractStage(), ResourceBundle.getBundle("bundles.interaction",
                Locale.getDefault()), this.getClass());
        logger.debug(BundleHelper.getApplication("languageChoiceLangChanged"), "polish");
    }

    /**
     * Changing overall interface language to English.
     */
    @FXML
    public void setLanguageEnglish() throws IOException {
        logger.debug(BundleHelper.getApplication("languageChoiceLangChanging"), "english");
        Locale.setDefault(new Locale("en"));
        StageCreator.createStage(extractStage(), ResourceBundle.getBundle("bundles.interaction",
                Locale.getDefault()), this.getClass());
        logger.debug(BundleHelper.getApplication("languageChoiceLangChanged"), "english");
    }

    public Stage extractStage() {
        return (Stage) this.mainController.getMainPaneWindow().getScene().getWindow();
    }


}
