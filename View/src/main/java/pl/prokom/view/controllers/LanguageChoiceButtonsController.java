package pl.prokom.view.controllers;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.prokom.view.stage.StageCreator;

public class LanguageChoiceButtonsController {

    private static String clickedToggleID = null;
    /**
     * ToggleButtons responsible for changing theme language.
     */
    @FXML
    ToggleButton tgbPolish;
    @FXML
    ToggleButton tgbEnglish;
    @FXML
    ToggleGroup languageSet;
    @FXML
    Pane setLanguagePolish;

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
        languageSet.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (oldToggle != null) {
                ToggleButton oldButton = (ToggleButton) oldToggle;
                oldButton.setDisable(false);
            }
            if (newToggle != null) {
                ToggleButton newButton = (ToggleButton) newToggle;
                newButton.setDisable(true);
                clickedToggleID = newButton.getId();
            }
        });
    }

    /**
     * Setting parent controller MainPaneWindowController type to LanguageChoiceButtonsController.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
        languageSet.getToggles().stream()
                .map(x -> (ToggleButton) x)
                .filter(x -> x.getId().equals(clickedToggleID))
                .findFirst()
                .ifPresentOrElse(x -> x.setDisable(true), () -> {
                    tgbPolish.setDisable(true);
                });
    }

    /**
     * Changing overall interface language to Polish.
     */
    @FXML
    public void setLanguagePolish() throws IOException {
        Locale.setDefault(new Locale("pl"));
        StageCreator.createStage(extractStage(), bundle.getBundle("bundles.interaction",
                Locale.getDefault()), this.getClass());
    }

    /**
     * Changing overall interface language to English.
     */
    @FXML
    public void setLanguageEnglish() throws IOException {
        Locale.setDefault(new Locale("en"));
        StageCreator.createStage(extractStage(), bundle.getBundle("bundles.interaction",
                Locale.getDefault()), this.getClass());
    }

    public Stage extractStage() {
        return (Stage) this.mainController.getMainPaneWindow().getScene().getWindow();
    }


}
