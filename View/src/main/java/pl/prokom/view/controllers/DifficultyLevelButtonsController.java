package pl.prokom.view.controllers;

import java.util.EnumMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.view.adapter.level.SudokuBoardLevel;
import pl.prokom.view.bundles.BundleHelper;

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

    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    private MainPaneWindowController mainController;

    /**
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        logger.trace(BundleHelper.getApplication("initializingParentController"));
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
        logger.trace(BundleHelper.getApplication("startingInitialization"));

        levelsToButtonsMap = new EnumMap<>(SudokuBoardLevel.class);
        levelsToButtonsMap.put(SudokuBoardLevel.EASY, tgbEasy);
        levelsToButtonsMap.put(SudokuBoardLevel.MEDIUM, tgbMedium);
        levelsToButtonsMap.put(SudokuBoardLevel.HARD, tgbHard);

        levelsToButtonsMap.forEach((x, y) -> y.setOnAction(e -> changeDifficultyLevel(x, true)));

        logger.trace(
                BundleHelper.getApplication("difficultyLevelNumber"), levelsToButtonsMap.size());
        logger.trace(BundleHelper.getApplication("finishedInitialization"));
    }

    /**
     * Method to execute SudokuBoard difficulty change.
     *
     * @param sudokuBoardLevel difficulty level of sudokuBoard
     */
    public void changeDifficultyLevel(SudokuBoardLevel sudokuBoardLevel, boolean propagate) {
        logger.debug(BundleHelper.getApplication("difficultyLevelStartedChanging"),
                clickedToggleID, sudokuBoardLevel);

        difficultyLevels.getToggles().stream().map(x -> (ToggleButton) x).forEach(x -> {
            x.setDisable(false);
            x.setSelected(false);
        });
        ToggleButton clickedButton = levelsToButtonsMap.getOrDefault(sudokuBoardLevel, tgbEasy);
        clickedButton.setDisable(true);
        clickedButton.setSelected(true);
        clickedToggleID = clickedButton.getId();
        if (propagate) {
            this.mainController.getSudokuGridController().setBoardLevel(sudokuBoardLevel);
        }

        logger.debug(BundleHelper.getApplication("difficultyLevelFinishedChanging"),
                sudokuBoardLevel, propagate);
    }

}
