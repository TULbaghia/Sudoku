package pl.prokom.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import pl.prokom.model.board.SudokuBoardLevel;

public class DifficultyLevelButtonsController {

    /**
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    private MainPaneWindowController mainController;

    public DifficultyLevelButtonsController() {}

    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
    }

    /**
     * ToggleButtons responsible for changing SudokuBoard diff. level.
     */
    @FXML
    ToggleButton tgbEasy;
    ToggleButton tgbMedium;
    ToggleButton tgbHard;

    @FXML
    public void setLevelEasy() {
        mainController.getSudokuBoardController().initSudokuCells(SudokuBoardLevel.EASY);
    }

    @FXML
    public void setLevelMedium() {
        mainController.getSudokuBoardController().initSudokuCells(SudokuBoardLevel.MEDIUM);
    }

    @FXML
    public void setLevelHard() {
        mainController.getSudokuBoardController().initSudokuCells(SudokuBoardLevel.HARD);
    }

    @FXML
    public void initialize(){}

}
