package pl.prokom.view.controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.board.SudokuBoardLevel;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.model.solver.SudokuSolver;

/**
 * Controller for main GUI class, which holds sudokuBoard stable.
 */
public class SudokuBoardController {

    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    MainPaneWindowController mainController;

    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
    }

    /**
     * GridPane instance with all sudokuBoard cells as a TextField instances.
     */
    @FXML
    GridPane gridPane;

    private SudokuBoard sudokuBoard;

//    /**
//     * Intializing controller functions.
//     * - init sudokuBoard,
//     * - filling sudoku GUI table,
//     */
//    @FXML
//    public void initialize() {
//        sudokuBoardLevel = SudokuBoardLevel.EASY;
//        initSudokuCells(sudokuBoardLevel);
//    }

    /**
     * Filling sudokuBoard gridPane by textFields with chosen number of ciphers.
     *
     * @param sudokuBoardLevel - difficuly level which is chosen by user (default = EASY).
     */
    public void initSudokuCells(SudokuBoardLevel sudokuBoardLevel) {
        StringConverter converter = new IntegerStringConverter();
        JavaBeanIntegerPropertyBuilder builder = JavaBeanIntegerPropertyBuilder.create();

        SudokuSolver<SudokuBoard> sudokuSolver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();

        Integer cellsNumber = sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize();
        List<Integer> randomValues =
                IntStream.range(0, cellsNumber).boxed().collect(Collectors.toList());
        Collections.shuffle(randomValues);
        List<Integer> randomSetValues, userInputValues;
        randomSetValues = randomValues.subList(0, sudokuBoardLevel.getFilledCells());
        userInputValues = randomValues.subList(sudokuBoardLevel.getFilledCells(), cellsNumber);

        gridPane.getChildren()
                .filtered(node -> node instanceof TextField)
                .forEach(node -> ((TextField) node).clear());

        randomSetValues.forEach(c -> {
                    TextField textField;
                    textField = new TextField(String.valueOf(sudokuBoard.get(c / 9, c % 9)));
                    textField.setAlignment(Pos.CENTER);
                    textField.setBackground(Background.EMPTY);
                    textField.setFont(new Font("Calibri", 20));
                    textField.setEditable(false);
                    gridPane.add(textField, c / 9, c % 9);
                }
        );

        userInputValues.forEach(c -> {
                    TextField textField = new TextField("");
                    textField.setAlignment(Pos.CENTER);
                    textField.setBackground(Background.EMPTY);
                    textField.setFont(new Font("Calibri", 20));
                    textField.setStyle("-fx-text-fill: green; -fx-font-size: 21 px;");
                    textField.setEditable(true);
                    gridPane.add(textField, c / 9, c % 9);

                    try {
                        JavaBeanIntegerProperty integerProperty = builder.bean(sudokuBoard.getSudokuField(c / 9, c % 9))
                                        .name("value").getter("getFieldValue").setter("setFieldValue").build();
                        textField.textProperty().bindBidirectional(integerProperty, converter);
                    } catch (NoSuchMethodException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
        );
    }
}
