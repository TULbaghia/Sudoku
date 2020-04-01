package pl.prokom.sudoku;

import java.util.Arrays;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.prokom.sudoku.partials.SudokuBox;
import pl.prokom.sudoku.partials.SudokuColumn;
import pl.prokom.sudoku.partials.SudokuField;
import pl.prokom.sudoku.partials.SudokuRow;


/**
 * SudokuBoard class to solve sudoku.
 */
public class SudokuBoard implements Cloneable {
    /**
     * Dimensions of miniSquare inside board.
     */
    private static final int miniSquareSize = 3;

    /**
     * Count of miniSquares inside board.
     */
    private static final int miniSquareCount = 3;

    /**
     * Size of game board.
     */
    private static int squareSize;

    /**
     * Sudoku game board.
     */
    private SudokuField[][] board;

    /**
     * Constructor of SudokuBoard object.
     */
    public SudokuBoard() {
        this.squareSize = miniSquareSize * miniSquareCount;
        setBoard(new SudokuField[squareSize][squareSize]);
    }

    /**
     * Returns output state of a board (solved correctly or not).
     *
     * @return boolean whether board is solved correctly or not
     */
    public final boolean solveGame() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        for (int i = 0; i < this.squareSize; i++) {
            for (int j = 0; j < this.squareSize; j++) {
                if (solver.isAllowed(i, j, this.board[i][j].getFieldValue(), this)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Getter for miniSquareSize.
     *
     * @return int mini SquareSize
     */
    public static int getMiniSquareSize() {
        return miniSquareSize;
    }

    public static int getMiniSquareCount() {
        return miniSquareCount;
    }

    /**
     * Getter for suquareSize.
     *
     * @return int boardWidth = boardHeight
     */
    public static int getSquareSize() {
        return squareSize;
    }

    /**
     * Returns element in game board.
     *
     * @param row    n-th row (from 0)
     * @param column n-th column (from 0)
     * @return value at board.[row][column]
     */
    public final SudokuField getBoardCell(int row, int column) {
        return this.board[row][column];
    }

    public final int get(int row, int column) {
        return getBoardCell(row, column).getFieldValue();
    }

    /**
     * Change element in board.
     *
     * @param row    n-th row (from 0)
     * @param column n-th column (from 0)
     * @param num    element you want to put inside board
     */
    public final void setBoardCell(int row, int column, int num) {
        this.board[row][column].setFieldValue(num);
    }

    public final void set(int row, int column, int num) {
        setBoardCell(row, column, num);
    }

    /**
     * Change game board.
     *
     * @param board array[squareSize][squareSize] that will override current board
     */
    public final void setBoard(SudokuField[][] board) {
        this.board = board;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (this.board[i][j] == null) {
                    this.board[i][j] = new SudokuField();
                }
            }
        }
    }

    /**
     * Returns reference to deep copy of this.board.
     *
     * @return int[][]
     */
    public final SudokuField[][] getCopyOfBoard() {
        //SudokuField[][] copyBoard = board.clone();
        SudokuField[][] copyBoard = new SudokuField[9][9];
        //board.clone() creates shallow copy of object, we need deep copy.
        //clone does not handle provide uniq references
        for (int i = 0; i < squareSize; i++) {
            //copyBoard[i] = board[i].clone();
            for (int j = 0; j < squareSize; j++) {
                copyBoard[i][j] = board[i][j].clone();
            }
        }
        return copyBoard;
    }

    public SudokuRow getRow(int row) {
        SudokuField[] sudokuFields = new SudokuField[getSquareSize()];

        for (int i = 0; i < getSquareSize(); i++) {
            sudokuFields[i] = getBoardCell(row, i).clone();
        }

        return new SudokuRow(sudokuFields);
    }

    public SudokuColumn getColumn(int column) {
        SudokuField[] sudokuFields = new SudokuField[getSquareSize()];

        for (int i = 0; i < getSquareSize(); i++) {
            sudokuFields[i] = getBoardCell(i, column).clone();
        }

        return new SudokuColumn(sudokuFields);
    }

    public SudokuBox getBox(int row, int column) {
        SudokuField[] sudokuFields = new SudokuField[getSquareSize()];

        int miniRow = row - row % getMiniSquareSize();
        int miniColumn = column - column % getMiniSquareSize();
        int index = 0;

        for (int i = miniRow; i < miniRow + getMiniSquareSize(); i++) {
            for (int j = miniColumn; j < miniColumn + getMiniSquareSize(); j++) {
                sudokuFields[index++] = getBoardCell(i, j).clone();
            }
        }

        return new SudokuBox(sudokuFields);
    }

    @Override
    public String toString() {
        return "SudokuBoard{"
                + "  miniSquareSize=" + miniSquareSize
                + ", miniSquareCount=" + miniSquareCount
                + ", squareSize=" + squareSize
                + ", board=" + Arrays.deepToString(board)
                + '}';
    }

    @Override
    public final Object clone() throws CloneNotSupportedException {
        SudokuBoard sudokuBoard = (SudokuBoard) super.clone();
        //clone create shallow copy of array, we need deep copy
        sudokuBoard.setBoard(getCopyOfBoard());
        return super.clone();
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder()
                .append(miniSquareSize, that.miniSquareSize)
                .append(miniSquareCount, that.miniSquareCount)
                .append(squareSize, that.squareSize)
                .append(Arrays.deepEquals(board, that.board), true)
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(miniSquareSize)
                .append(miniSquareCount)
                .append(squareSize)
                .append(board)
                .toHashCode();
    }

}





