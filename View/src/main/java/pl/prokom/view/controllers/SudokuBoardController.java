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
     * User-chosen currently setting number.
     * button1-9
     */
    @FXML
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9;

    /**
     * GridPane instance with all sudokuBoard cells as a TextField instances.
     */
    @FXML
    GridPane gridPane;

    /**
     * User menu RadioButtons.
     */
    @FXML
    RadioButton rb_easy, rb_medium, rb_hard, rb_english, rb_polish;

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
        rb_easy.setSelected(true);
        rb_easy.setDisable(true);
        rb_polish.setSelected(true);
    }

    /**
     * Filling sudokuBoard, depends on difficulty level, chosen by setting specific RadioButton instance.
     */
    @FXML
    public void onActionRadiobuttonEasy() {
        if (rb_easy.isSelected()) {
            initSudokuCells(SudokuBoardLevel.EASY);
            rb_medium.setSelected(false);
            rb_hard.setSelected(false);
            rb_medium.setDisable(false);
            rb_hard.setDisable(false);
            rb_easy.setDisable(true);
        }
    }

    @FXML
    public void onActionRadiobuttonMedium() {
        if (rb_medium.isSelected()) {
            initSudokuCells(SudokuBoardLevel.MEDIUM);
            rb_easy.setSelected(false);
            rb_hard.setSelected(false);
            rb_easy.setDisable(false);
            rb_hard.setDisable(false);
            rb_medium.setDisable(true);
        }
    }

    @FXML
    public void onActionRadiobuttonHard() {
        if (rb_hard.isSelected()) {
            initSudokuCells(SudokuBoardLevel.HARD);
            rb_easy.setSelected(false);
            rb_medium.setSelected(false);
            rb_easy.setDisable(false);
            rb_medium.setDisable(false);
            rb_hard.setDisable(true);
        }
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

}
