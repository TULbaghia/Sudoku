package pl.prokom.view.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LanguageChoiceButtonsController {

    public ToggleGroup languageSet;
    /**
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    private MainPaneWindowController mainPaneWindow;

    public LanguageChoiceButtonsController() {}

    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainPaneWindow = mainPaneWindowController;
    }

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

    @FXML
    public void initialize(){
    }
}
