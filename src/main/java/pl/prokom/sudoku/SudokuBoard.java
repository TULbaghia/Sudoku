package pl.prokom.sudoku;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;


/**
 * SudokuBoard class to solve sudoku.
 */
public class SudokuBoard implements Cloneable {
    /**
     * Size of miniSquare inside board.
     */
    private final int miniSquareSize = 3;
    /**
     * Count of miniSquares inside board.
     */
    private final int miniSquareCount = 3;

    /**
     * Size of board.
     */
    private final int squareSize;
    /**
     * Sudoku board.
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
     * Returns output state of a board (solved correctly or not)
     *
     * @return boolean
     */

    public final boolean solveGame() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        for (int i = 0; i < this.squareSize; i++) {
            for (int j = 0; j < this.squareSize; j++) {
                if (solver.isAllowed(i, j, this.board[i][j], this)) return false;
            }
        }
        return true;
    }

    /**
     * Getters and setter used on BacktrackingSudokuSolver class.
     */
    public final int getMiniSquareSize() {
        return miniSquareSize;
    }

    public final int getSquareSize() {
        return squareSize;
    }

    public final void setBoard(int[][] board) {
        this.board = board;
    }

    public final void setBoardCell(int i, int j, int num){
        this.board[i][j] = num;
    }
    public final int getBoardCell(int i, int j){
        return this.board[i][j];
    }

    /**
     * Returns reference to copy of this.board.
     *
     * @return int[][]
     */
    public final int[][] getCopyOfBoard() {
        int[][] copyBoard = new int[squareSize][];
        for (int i = 0; i < squareSize; i++) {
            copyBoard[i] = Arrays.copyOf(board[i], squareSize);
        }
        return copyBoard;
    }

    @Override
    public String toString() {
        return "SudokuBoard{" +
                "miniSquareSize=" + miniSquareSize +
                ", miniSquareCount=" + miniSquareCount +
                ", squareSize=" + squareSize +
                ", board=" + Arrays.toString(board) +
                '}';
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





