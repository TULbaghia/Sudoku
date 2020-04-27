package pl.prokom.view.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class DifficultyLevelButtonsController {

    /**
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    private MainPaneWindowController mainController;

    public DifficultyLevelButtonsController(MainPaneWindowController mainController) {
        this.mainController = mainController;
    }

    /**
     * ToggleButtons responsible for changing SudokuBoard diff. level.
     */
    @FXML
    ToggleButton tgbEasy, tgbMedium, tgbHard;

    @FXML
    public void setLevelEasy() {

    }

    @FXML
    public void setLevelMedium() {

    }

    @FXML
    public void setLevelHard() {

    }

    @FXML
    public void initialize(){
    }
}
