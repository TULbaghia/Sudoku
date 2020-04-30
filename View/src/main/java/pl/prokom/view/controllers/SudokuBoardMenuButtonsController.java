package pl.prokom.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SudokuBoardMenuButtonsController {

    @FXML
    private Button correctnessButton;

    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    private MainPaneWindowController mainController;

    /**
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
    }

    @FXML
    public void checkCorrectness(){

    }

}
