package pl.prokom.view.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import pl.prokom.view.menu.MainPaneWindow;

public class MainPaneWindowController extends MainPaneWindow implements Initializable {
    /**
     * Basic Pane instance.
     */
    @FXML
    private Pane mainPaneWindow;

    @FXML
    private ResourceBundle interactionBundle;

    /**
     * Reference to class, that controls difficulty level buttons- DifficultyLevelButtonsController.
     */
    @FXML
    private DifficultyLevelButtonsController difficultyLevelsController;

    /**
     * Reference to class, which controls language choice buttons - LanguageChoiceButtonsController.
     */
    @FXML
    private LanguageChoiceButtonsController languageChoiceController;

    /**
     * Reference to class, which controls SudokuBoard - SudokuBoardController.
     */
    @FXML
    private SudokuBoardController sudokuGridController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.interactionBundle = resourceBundle;

        sudokuGridController.setParentController(this);
        languageChoiceController.setParentController(this);
        difficultyLevelsController.setParentController(this);

        difficultyLevelsController.triggerButton();
    }

    public Pane getMainPaneWindow() {
        return mainPaneWindow;
    }

    public DifficultyLevelButtonsController getDifficultyLevelsController() {
        return difficultyLevelsController;
    }

    public LanguageChoiceButtonsController getLanguageChoiceController() {
        return languageChoiceController;
    }

    public SudokuBoardController getSudokuGridController() {
        return sudokuGridController;
    }

    public ResourceBundle getInteractionBundle() {
        return interactionBundle;
    }
}
