package pl.prokom.view.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.view.bundles.BundleHelper;
import pl.prokom.view.controllers.sudokuboard.SudokuBoardController;
import pl.prokom.view.controllers.sudokuboard.SudokuBoardCorrectnessController;
import pl.prokom.view.controllers.sudokuboard.SudokuBoardDaoDBController;
import pl.prokom.view.controllers.sudokuboard.SudokuBoardDaoFileController;
import pl.prokom.view.menu.MainPaneWindow;

public class MainPaneWindowController extends MainPaneWindow implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(MainPaneWindowController.class);

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
    /**
     * Reference to class, that controls SudokuBoard menu buttons- SudokuBoardMenuButtonsController.
     */
    @FXML
    private SudokuBoardDaoFileController sudokuBoardDaoFileController;

    /**
     * Reference to class, that controls SudokuBoard database read/write features.
     */
    @FXML
    private SudokuBoardDaoDBController sudokuBoardDaoDBController;
    /**
     * Reference to class, that controls Correctness Mode of sudokuBoard.
     */
    @FXML
    private SudokuBoardCorrectnessController correctnessController;

    @FXML
    private AuthorsController authorsBundleController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.trace(BundleHelper.getApplication("startingInitialization"));
        this.interactionBundle = resourceBundle;

        languageChoiceController.setParentController(this);

        sudokuGridController.setParentController(this);
        correctnessController.setParentController(this);
        authorsBundleController.setParentController(this);
        sudokuBoardDaoFileController.setParentController(this);
        sudokuBoardDaoDBController.setParentController(this);

        difficultyLevelsController.setParentController(this);
        logger.trace(BundleHelper.getApplication("finishedInitialization"));
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

    public SudokuBoardCorrectnessController getCorrectnessController() {
        return correctnessController;
    }

    public SudokuBoardController getSudokuGridController() {
        return sudokuGridController;
    }

    public ResourceBundle getInteractionBundle() {
        return interactionBundle;
    }
}
