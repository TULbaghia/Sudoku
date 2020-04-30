package pl.prokom.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.prokom.view.stage.StageCreator;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageChoiceButtonsController {
    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    MainPaneWindowController mainController;

    private ResourceBundle bundle = ResourceBundle.getBundle("bundles.interaction");

    /**
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
    }
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

    @FXML
    public void setLanguagePolish() throws IOException {
        Locale.setDefault(new Locale("pl"));
        StageCreator.createStage(extractStage(), bundle.getBundle("bundles.interaction", Locale.getDefault()), this.getClass());
    }

    @FXML
    public void setLanguageEnglish() throws IOException {
        Locale.setDefault(new Locale("en"));
        StageCreator.createStage(extractStage(), bundle.getBundle("bundles.interaction", Locale.getDefault()), this.getClass());
    }

    public Stage extractStage(){
        return (Stage) this.mainController.getMainPaneWindow().getScene().getWindow();
    }


}
