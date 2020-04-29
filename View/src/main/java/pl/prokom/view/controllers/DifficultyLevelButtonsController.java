package pl.prokom.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import pl.prokom.model.board.SudokuBoardLevel;

public class DifficultyLevelButtonsController {

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
     * ToggleButtons responsible for changing SudokuBoard diff. level.
     */
    @FXML
    ToggleButton tgbEasy;
    @FXML
    ToggleButton tgbMedium;
    @FXML
    ToggleButton tgbHard;
    @FXML
    Pane difficultyLevelPane;

    @FXML
    public void setLevelEasy() {
        this.mainController.getSudokuGridController().initSudokuCells(SudokuBoardLevel.EASY);
    }

    @FXML
    public void setLevelMedium() { this.mainController.getSudokuGridController().initSudokuCells(SudokuBoardLevel.MEDIUM); }

    @FXML
    public void setLevelHard() {
        this.mainController.getSudokuGridController().initSudokuCells(SudokuBoardLevel.HARD);
    }

    public Pane getDifficultyLevelPane() {
        return difficultyLevelPane;
    }
}
