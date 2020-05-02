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
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.view.converter.FieldStringConverter;
import javafx.beans.binding.Bindings;

/**
 * Controller for main GUI class, which holds sudokuBoard stable.
 */
public class SudokuBoardController {

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
     * Keeps SudokuBoard object
     */
    private SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuBoard sudokuFromFile;

    /**
     * Keeps references to JBIP, due to WeakReference in Observable
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<JavaBeanIntegerProperty> javaBeanIntegerProperties;

    JavaBeanIntegerPropertyBuilder builder = new JavaBeanIntegerPropertyBuilder();

    /**
     * Stores references to TextFields to easly manipulate data during reload
     */
    private List<TextField> textFields;

    /**
     * Stores references to current difficulty level od SudokuBoard.
     */
    private SudokuBoardLevel boardCurrentLevel;

    @SuppressWarnings("rawtypes")
    StringConverter converter = new FieldStringConverter();

    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
    }

    /**
     * Filling sudokuBoard gridPane by textFields with chosen number of ciphers.
     *
     * @param sudokuBoardLevel - difficuly level which is chosen by user (default = EASY).
     */
    public void initSudokuCells(SudokuBoardLevel sudokuBoardLevel) {
        IntStream.range(0, sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize()).forEach(x -> sudokuBoard.reset(x / 9, x % 9));
        SudokuBoard sudokuBoardTmp;
        if (sudokuFromFile == null) {
            sudokuBoardTmp = new SudokuBoard(new BacktrackingSudokuSolver());
            sudokuBoardTmp.solveGame();
        } else sudokuBoardTmp = this.sudokuFromFile;
        this.sudokuFromFile = null;

        int cellsNumber = sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize();
        List<Integer> randomValues =
                IntStream.range(0, cellsNumber).boxed().collect(Collectors.toList());
        Collections.shuffle(randomValues);

        List<Integer> randomSetValues, userInputValues;
        randomSetValues = randomValues.subList(0, sudokuBoardLevel.getFilledCells());
        userInputValues = randomValues.subList(sudokuBoardLevel.getFilledCells(), cellsNumber);

        randomSetValues.forEach(c -> {
            textFields.get(c).setEditable(false);
            textFields.get(c).setStyle("");
            javaBeanIntegerProperties.get(c).set(sudokuBoardTmp.get(c / 9, c % 9));
        });

        userInputValues.forEach(c -> {
            textFields.get(c).setStyle("-fx-text-fill: red; -fx-font-size: 20 px;");
            textFields.get(c).setEditable(true);
            javaBeanIntegerProperties.get(c).fireValueChangedEvent();
        });

        //Temporarly alternative to bindBidirectional
        randomValues.forEach(x -> textFields.get(x).setText(converter.toString(javaBeanIntegerProperties.get(x).get())));

    }

    @FXML
    public void initialize() {
        textFields = Arrays.asList(new TextField[sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize()]);
        javaBeanIntegerProperties = Arrays.asList(new JavaBeanIntegerProperty[sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize()]);

        IntStream.range(0, sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize()).forEach(x -> {
            TextField textField = new TextField(String.valueOf(sudokuBoard.get(x / 9, x % 9)));
            textField.setAlignment(Pos.CENTER);
            textField.setBackground(Background.EMPTY);
            textField.setFont(new Font("Calibri", 20));
            textField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("[0-9]?") ? change : null));
            gridPane.add(textField, x / 9, x % 9);
            textFields.set(x, textField);

            textField.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
                textField.setText(converter.toString(javaBeanIntegerProperties.get(x).get()));
            });

            try {
                JavaBeanIntegerProperty integerProperty = builder.bean(sudokuBoard.getSudokuField(x / 9, x % 9))
                        .name("value").getter("getFieldValue").setter("setFieldValue").build();

                //Temporarly alternative to bindBidirectional
                textField.textProperty().addListener((observableValue, s, t1) -> {
                    try {
                        sudokuBoard.set(x / 9, x % 9, (Integer) converter.fromString(t1));
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                });

                //Temporarly disabled due to no idea how to handle exception
//                textField.textProperty().bindBidirectional(integerProperty, converter);
                javaBeanIntegerProperties.set(x, integerProperty);
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    /**
     * Set deserialized SudokuBoard instance to SudokuBoardController field. (in case of initializing it)
     */
    public void setSudokuFromFile(SudokuBoard sudokuFromFile){
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
