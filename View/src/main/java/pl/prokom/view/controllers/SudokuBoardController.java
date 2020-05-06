package pl.prokom.view.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.board.SudokuBoardLevel;
import pl.prokom.model.exception.IllegalFieldValueException;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuBoard sudokuFromFile = null;

    /**
     * Keeps references to JBIP, due to WeakReference in Observable.
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<JavaBeanIntegerProperty> jBIntegerProperties;

    JavaBeanIntegerPropertyBuilder builder = new JavaBeanIntegerPropertyBuilder();

    /**
     * Stores references to TextFields to easly manipulate data during reload.
     */
    private List<TextField> textFields;

    /**
     * Stores references to current difficulty level od SudokuBoard.
     */
    private SudokuBoardLevel boardCurrentLevel;

    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
    }

    /**
     * Filling sudokuBoard gridPane by textFields with chosen number of ciphers.
     *
     * @param sudokuBoardLevel - difficuly level which is chosen by user (default = EASY).
     */
    public void initSudokuCells(SudokuBoardLevel sudokuBoardLevel) {

        IntStream.range(0, sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize())
                .forEach(x -> jBIntegerProperties.get(x).set(0));
        SudokuBoard sudokuBoardTmp;

        int cellsNumber = sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize();
        List<Integer> randomValues =
                IntStream.range(0, cellsNumber).boxed().collect(Collectors.toList());
        Collections.shuffle(randomValues);


        if (sudokuFromFile == null) {
            sudokuBoardTmp = new SudokuBoard(new BacktrackingSudokuSolver());
            sudokuBoardTmp.solveGame();

            List<Integer> randomSetValues, userInputValues;
            randomSetValues = randomValues.subList(0, sudokuBoardLevel.getFilledCells());
            userInputValues = randomValues.subList(sudokuBoardLevel.getFilledCells(), cellsNumber);

            randomSetValues.forEach(c -> {
                textFields.get(c).setStyle("");
                textFields.get(c).setEditable(false);
                jBIntegerProperties.get(c).set(sudokuBoardTmp.get(c / 9, c % 9));
            });

            userInputValues.forEach(c -> {
                textFields.get(c).setStyle("-fx-text-fill: red; -fx-font-size: 20 px;");
                textFields.get(c).setEditable(true);
            });
        } else {
            sudokuBoardTmp = sudokuFromFile;
            sudokuFromFile = null;

            randomValues.forEach(c -> {
                if (sudokuBoardTmp.get(c / 9, c % 9) == 0) {
                    textFields.get(c).setStyle("-fx-text-fill: red; -fx-font-size: 20 px;");
                    textFields.get(c).setEditable(true);
                } else {
                    textFields.get(c).setStyle("");
                    textFields.get(c).setEditable(false);
                }
                jBIntegerProperties.get(c).set(sudokuBoardTmp.get(c / 9, c % 9));
            });
        }
    }

    @FXML
    public void initialize() {
        logger.info("Initializtion of SudokuBoardController.");
        textFields = Arrays.asList(
                new TextField[sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize()]);
        jBIntegerProperties = Arrays.asList(
                new JavaBeanIntegerProperty[
                        sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize()]);

        IntStream.range(0, sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize()).forEach(x -> {
            TextField textField = new TextField(
                    String.valueOf(sudokuBoard.get(x / 9, x % 9)));
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
                            System.err.println(e.getMessage());
                            numVal = 0;
                        }
                        return numVal;
                    }
                };

                textField.textProperty().bindBidirectional(integerProperty, converter);

                textField.focusedProperty().addListener(observable -> {
                    textField.setText(converter.toString(integerProperty.get()));
                });

                jBIntegerProperties.set(x, integerProperty);
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    /**
     * Set deserialized SudokuBoard instance to SudokuBoardController field.
     * (in case of initializing it)
     */
    public void setSudokuFromFile(SudokuBoard sudokuFromFile) {
        this.sudokuFromFile = sudokuFromFile;
    }

    /**
     * Changing difficulty level of SudokuBoard (DifficultyLevelButtonsController).
     */
    public void setBoardCurrentLevel(SudokuBoardLevel boardCurrentLevel) {
        this.boardCurrentLevel = boardCurrentLevel;
    }

    /**
     * Get current difficulty level of actual board.
     */
    public SudokuBoardLevel getBoardCurrentLevel() {
        return boardCurrentLevel;
    }

    public SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }
}
