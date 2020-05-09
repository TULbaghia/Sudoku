package pl.prokom.view.controllers.sudokuboard;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.view.adapter.SudokuBoardAdapter;
import pl.prokom.view.adapter.correctness.CorrectnessMode;
import pl.prokom.view.adapter.level.SudokuBoardLevel;
import pl.prokom.model.exception.IllegalFieldValueException;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.view.controllers.MainPaneWindowController;

/**
 * Controller for main GUI class, which holds sudokuBoard stable.
 */
public class SudokuBoardController {

    /**
     * Logger instance. Logging events of SudokuBoardController class.
     */
    private static final Logger logger = LoggerFactory.getLogger(SudokuBoardController.class);

    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    MainPaneWindowController mainController;

    /**
     * GridPane instance with all sudokuBoard cells as a TextField instances.
     */
    @FXML
    GridPane gridPane;

    /**
     * Keeps SudokuBoard object.
     */
    private static SudokuBoardAdapter sudokuBoard = new SudokuBoardAdapter();

    /**
     * Keeps references to JBIP, due to WeakReference in Observable.
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<JavaBeanIntegerProperty> beanIntegerProperties;

    /**
     * Stores references to TextFields to easly manipulate data during reload.
     */
    private List<TextField> textFields;

    /**
     * Setting parent controller of MainPaneWindowController type to SudokuBoardController.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
    }

    /**
     * Filling sudokuBoard gridPane by textFields with chosen number of ciphers.
     */
    public void initSudokuCells() {
        SudokuBoard sudokuBoardTmp = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoardTmp.solveGame();

        initializeSudokuCellsWith(sudokuBoardTmp, true);
    }

    public void initializeSudokuCellsWith(SudokuBoard tmp, boolean wipeEmpty) {
        IntStream.range(0, sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize())
                .forEach(x -> beanIntegerProperties.get(x).set(0));

        sudokuBoard.getProtectedIntegerFields().forEach(c -> {
            textFields.get(c).setStyle("");
            textFields.get(c).setEditable(false);
            beanIntegerProperties.get(c).set(tmp.get(c / 9, c % 9));
        });

        sudokuBoard.getEditableIntegerFields().forEach(c -> {
            textFields.get(c).setStyle("-fx-text-fill: red; -fx-font-size: 20 px;");
            textFields.get(c).setEditable(true);
            if(!wipeEmpty) {
                beanIntegerProperties.get(c).set(tmp.get(c / 9, c % 9));
            }
        });
    }

    /**
     * Initializing of SudokuBoardGridPane.fxml using SudokuBoardController.
     * Additional features:
     * - initializing of internal SudokuBoard instance,
     * - creating properties for each SudokuField value,
     * - binding properties with TextField instances created programatically in this method,
     * - implementation of converter included;
     */
    @FXML
    public void initialize() {
        logger.info("Initializtion of SudokuBoardController.");

        JavaBeanIntegerPropertyBuilder builder = new JavaBeanIntegerPropertyBuilder();

        textFields = Arrays.asList(
                new TextField[sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize()]);
        beanIntegerProperties = Arrays.asList(
                new JavaBeanIntegerProperty[
                        sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize()]);

        IntStream.range(0, sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize()).forEach(x -> {
            TextField textField = new TextField(
                    String.valueOf(sudokuBoard.get(x / 9, x % 9)));
            logger.info("Initialize SudokuBoard with {}", sudokuBoard.get(x / 9, x % 9));
            textField.setAlignment(Pos.CENTER);
            textField.setBackground(Background.EMPTY);
            textField.setFont(new Font("Calibri", 20));
            textField.setTextFormatter(new TextFormatter<>(change ->
                    change.getControlNewText().matches("[0-9]?") ? change : null));
            gridPane.add(textField, x / 9, x % 9);
            textFields.set(x, textField);

            try {
                JavaBeanIntegerProperty integerProperty = builder
                        .bean(sudokuBoard.getSudokuField(x / 9, x % 9))
                        .name("value").getter("getFieldValue").setter("setFieldValue")
                        .build();

                StringConverter<Number> converter = new StringConverter<>() {
                    @Override
                    public String toString(Number number) {
                        int value = number.intValue();
                        return value == 0 ? "" : Integer.toString(value);
                    }

                    @Override
                    public Integer fromString(String value) {
                        if (value == null || value.trim().length() < 1) {
                            return 0;
                        }
                        int numVal = Integer.parseInt(value.trim());
                        try {
                            sudokuBoard.getSudokuField(x / 9, x % 9).validate(numVal);
                        } catch (IllegalFieldValueException e) {
                            logger.error(e.getMessage());
                            numVal = 0;
                        }
                        return numVal;
                    }
                };

                textField.textProperty().bindBidirectional(integerProperty, converter);

                textField.focusedProperty().addListener(observable -> {
                    textField.setText(converter.toString(integerProperty.get()));
                });

                beanIntegerProperties.set(x, integerProperty);
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    /**
     * Changing difficulty level of SudokuBoard (DifficultyLevelButtonsController).
     */
    public void setBoardLevel(SudokuBoardLevel boardCurrentLevel) {
        if (boardCurrentLevel != getBoardCurrentLevel()) {
            sudokuBoard.setSudokuBoardLevel(boardCurrentLevel);
        }
        initSudokuCells();
    }

    public void setBoardListenerMode(CorrectnessMode listenerMode) {
        if(listenerMode != sudokuBoard.getSudokuCorrectnessMode()) {
            sudokuBoard.setSudokuFieldListenerEnabled(listenerMode);
        }
    }

    /**
     * Get current difficulty level of actual board.
     */
    public SudokuBoardLevel getBoardCurrentLevel() {
        return sudokuBoard.getSudokuBoardLevel();
    }

    public SudokuBoardAdapter getSudokuBoard() {
        return sudokuBoard;
    }
}
