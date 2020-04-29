package pl.prokom.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class LanguageChoiceButtonsController {
    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    MainPaneWindowController mainController;

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
    public void setLanguagePolish() {
    }

    @FXML
    public void setLanguageEnglish() {
    }


}
