package pl.prokom.sudoku;

import java.util.Arrays;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * SudokuBoard class to solve sudoku.
 */
public class SudokuBoard implements Cloneable {
    /**
     * Dimensions of miniSquare inside board.
     */
    private final int miniSquareSize = 3;

    /**
     * Count of miniSquares inside board.
     */
    private final int miniSquareCount = 3;

    /**
     * Size of game board.
     */
    private final int squareSize;

    /**
     * Sudoku game board.
     */
    private int[][] board;

    /**
     * Constructor of SudokuBoard object.
     */
    public SudokuBoard() {
        this.squareSize = miniSquareSize * miniSquareCount;
        this.board = new int[squareSize][squareSize];
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
                if (solver.isAllowed(i, j, this.board[i][j], this)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Getter for miniSquareSize.
     * @return int mini SquareSize
     */
    public final int getMiniSquareSize() {
        return miniSquareSize;
    }

    /**
     * Getter for suquareSize.
     * @return int boardWidth = boardHeight
     */
    public final int getSquareSize() {
        return squareSize;
    }

    /**
     * Returns element in game board.
     * @param row n-th row (from 0)
     * @param column n-th column (from 0)
     * @return value at board.[row][column]
     */
    public final int getBoardCell(int row, int column) {
        return this.board[row][column];
    }

    /**
     * Change element in board.
     * @param row n-th row (from 0)
     * @param column n-th column (from 0)
     * @param num element you want to put inside board
     */
    public final void setBoardCell(int row, int column, int num) {
        this.board[row][column] = num;
    }

    /**
     * Change game board.
     * @param board array[squareSize][squareSize] that will override current board
     */
    public final void setBoard(int[][] board) {
        this.board = board;
    }

    /**
     * Returns reference to copy of this.board.
     *
     * @return int[][]
     */
    public final int[][] getCopyOfBoard() {
        int[][] copyBoard = board.clone();
        for (int i = 0; i < squareSize; i++) {
            copyBoard[i] = board[i].clone();
        }
        return copyBoard;
    }

    @Override
    public String toString() {
        return "SudokuBoard{"
                + "miniSquareSize=" + miniSquareSize
                + ", miniSquareCount=" + miniSquareCount
                + ", squareSize=" + squareSize
                + ", board=" + Arrays.deepToString(board)
                + '}';
    }

    @Override
    public final Object clone() throws CloneNotSupportedException {
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
                .append(board, that.board)
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





