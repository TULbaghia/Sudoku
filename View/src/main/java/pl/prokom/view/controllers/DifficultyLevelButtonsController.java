package pl.prokom.view.controllers;

import java.util.EnumMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.view.adapter.level.SudokuBoardLevel;

public class DifficultyLevelButtonsController {

    private static final Logger logger =
            LoggerFactory.getLogger(DifficultyLevelButtonsController.class);

    public static String clickedToggleID = null;

    private Map<SudokuBoardLevel, ToggleButton> levelsToButtonsMap;

    @FXML
    private ToggleButton tgbEasy;
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
        logger.info("Setted mainController");
        difficultyLevels.getToggles().stream()
                .map(x -> (ToggleButton) x)
                .filter(x -> x.getId().equals(clickedToggleID))
                .findFirst()
                .ifPresentOrElse(ToggleButton::fire, () -> tgbEasy.fire());
        logger.info("Triggered button change event");
    }

    /**
     * Initialization method that set-up necessary listeners.
     */
    @FXML
    public void initialize() {
        levelsToButtonsMap = new EnumMap<>(SudokuBoardLevel.class);
        levelsToButtonsMap.put(SudokuBoardLevel.EASY, tgbEasy);
        levelsToButtonsMap.put(SudokuBoardLevel.MEDIUM, tgbMedium);
        levelsToButtonsMap.put(SudokuBoardLevel.HARD, tgbHard);

        levelsToButtonsMap.forEach((x, y) -> y.setOnAction(e -> changeDifficultyLevel(x, true)));
        logger.info("Initialized class with {} difficulty levels", levelsToButtonsMap.size());
    }

    /**
     * Method to execute SudokuBoard difficulty change.
     *
     * @param sudokuBoardLevel difficulty level of sudokuBoard
     */
    public void changeDifficultyLevel(SudokuBoardLevel sudokuBoardLevel, boolean propagate) {
        difficultyLevels.getToggles().stream().map(x -> (ToggleButton) x).forEach(x -> {
            x.setDisable(false);
            x.setSelected(false);
        });
        ToggleButton clickedButton = levelsToButtonsMap.getOrDefault(sudokuBoardLevel, tgbEasy);
        clickedButton.setDisable(true);
        clickedButton.setSelected(true);
        clickedToggleID = clickedButton.getId();
        logger.info("Changing difficulty level to {} with propagation: {}"
                , sudokuBoardLevel.name(), propagate);
        if(propagate) {
            this.mainController.getSudokuGridController().setBoardLevel(sudokuBoardLevel);
        }
    }

}
