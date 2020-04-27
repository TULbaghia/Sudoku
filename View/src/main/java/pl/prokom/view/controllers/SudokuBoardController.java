package pl.prokom.view.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.board.SudokuBoardLevel;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.model.solver.SudokuSolver;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Controller for main GUI class, which holds sudokuBoard stable.
 */
public class SudokuBoardController {

    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    private MainPaneWindowController mainController;

    /**
     * GridPane instance with all sudokuBoard cells as a TextField instances.
     */
    @FXML
    GridPane gridPane;

    private SudokuBoard sudokuBoard;
    private SudokuBoardLevel sudokuBoardLevel;

    /**
     * Intializing controller functions.
     * - init sudokuBoard,
     * - filling sudoku GUI table,
     */
    @FXML
    public void initialize() {
        sudokuBoardLevel = SudokuBoardLevel.EASY;
        initSudokuCells(sudokuBoardLevel);
    }

    /**
     * Filling sudokuBoard gridPane by textFields with chosen number of ciphers.
     *
     * @param sudokuBoardLevel - difficuly level which is chosen by user (default = EASY).
     */
    public void initSudokuCells(SudokuBoardLevel sudokuBoardLevel) {
        SudokuSolver<SudokuBoard> sudokuSolver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();

        Integer cellsNumber = sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize();
        List<Integer> randomValues =
                IntStream.range(0, cellsNumber).boxed().collect(Collectors.toList());
        Collections.shuffle(randomValues);
        randomValues = randomValues.subList(0, sudokuBoardLevel.getFilledCells());

        gridPane.getChildren()
                .filtered(node -> node instanceof TextField)
                .forEach(node -> ((TextField) node).clear());

        randomValues.stream().forEach(c -> {
                    TextField textField;
                    textField = new TextField(String.valueOf(sudokuBoard.get(c / 9, c % 9)));
                    textField.setAlignment(Pos.CENTER);
                    textField.setBackground(Background.EMPTY);
                    textField.setFont(new Font("Calibri", 20));
                    textField.setEditable(false);
                    gridPane.add(textField, c / 9, c % 9);
                }
        );
    }

    public void setMainController(MainPaneWindowController mainController) {
        this.mainController = mainController;
    }
}
