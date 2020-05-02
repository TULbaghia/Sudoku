package pl.prokom.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import pl.prokom.model.board.SudokuBoardLevel;

public class DifficultyLevelButtonsController {

    public static String clickedToggleID = null;

    @FXML
    ToggleButton tgbEasy;
    @FXML
    private ToggleButton tgbMedium;
    @FXML
    private ToggleButton tgbHard;
    @FXML
    private ToggleGroup difficultyLevels;
    @FXML
    private Pane difficultyLevelPane;

    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    private MainPaneWindowController mainController;

    /**
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
        difficultyLevels.getToggles().stream()
                .map(x -> (ToggleButton) x)
                .filter(x -> x.getId().equals(clickedToggleID))
                .findFirst()
                .ifPresentOrElse(ToggleButton::fire, () -> tgbEasy.fire());
    }

    /**
     * Initialization method that set-up necessary listeners.
     */
    @FXML
    public void initialize() {
        tgbEasy.setOnAction(actionEvent -> changeDifficultyLevel(SudokuBoardLevel.EASY));
        tgbMedium.setOnAction(actionEvent -> changeDifficultyLevel(SudokuBoardLevel.MEDIUM));
        tgbHard.setOnAction(actionEvent -> changeDifficultyLevel(SudokuBoardLevel.HARD));

        difficultyLevels.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (oldToggle != null) {
                ToggleButton oldButton = (ToggleButton) oldToggle;
                oldButton.setDisable(false);
            }
            if(newToggle != null) {
                ToggleButton newButton = (ToggleButton) newToggle;
                newButton.setDisable(true);
                clickedToggleID = newButton.getId();
            }
        });
    }

    /**
     * Method to execute SudokuBoard difficulty change.
     * @param sudokuBoardLevel
     */
    private void changeDifficultyLevel(SudokuBoardLevel sudokuBoardLevel) {
        this.mainController.getSudokuGridController().initSudokuCells(sudokuBoardLevel);
        this.mainController.getSudokuGridController().setBoardCurrentLevel(sudokuBoardLevel);
    }

}
