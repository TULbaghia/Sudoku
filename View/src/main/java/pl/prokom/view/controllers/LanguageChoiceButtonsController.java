package pl.prokom.view.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

public class LanguageChoiceButtonsController {

    /**
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    private MainPaneWindowController mainController;

    /**
     * ToggleButtons responsible for changing theme language.
     */
    @FXML
    ToggleButton tgbPolish, tgbEnglish;

    @FXML
    public void setLanguagePolish() {

    }

    @FXML
    public void setLanguageEnglish() {

    }

    public void setMainController(MainPaneWindowController mainController) {
        this.mainController = mainController;
    }
}
