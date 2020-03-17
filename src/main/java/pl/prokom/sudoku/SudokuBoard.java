package pl.prokom.sudoku;

import java.util.Arrays;
import java.util.Random;


/**
 * SudokuBoard class to solve sudoku.
 */
public class SudokuBoard {
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
     * Method to check whether you can put {@code num} in board or not.
     *
     * @param row int row to search for duplicates
     * @param column int column to search for duplicates
     * @param num int number you want to put in board
     * @return boolean
     */
    private boolean isAllowed(final int row, final int column, final int num) {

        // Check if number already in row
        for (int i = 0; i < squareSize; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // Check if number already in column
        for (int i = 0; i < squareSize; i++) {
            if (board[i][column] == num) {
                return false;
            }
        }

        // Check if number already in mini square
        // Set mini{Row,Column) to top left of mini square
        int miniRow = row - row % miniSquareSize;
        int miniColumn = column - column % miniSquareSize;

        // Iterate through mini square
        for (int i = miniRow; i < miniRow + miniSquareSize; i++) {
            for (int j = miniColumn; j < miniColumn + miniSquareSize; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Override for fillBoard method.
     */
    public final void fillBoard() {
        fillBoard(true);
    }


    /**
     * Generates/Fills sudoku board with numbers.
     *
     * @param override boolean true if you want to override values in board
     */
    public final void fillBoard(final boolean override) {
        Random random = new Random();
        if (override) {
            board = new int[squareSize][squareSize];
        }
        int[][] helpBoard = new int[squareSize][squareSize];

        for (int row = 0; row < squareSize; row++) {
            for (int column = 0; column < squareSize; column++) {
                if (board[row][column] != 0 && helpBoard[row][column] == 0) {
                    continue;
                }
                boolean isCorrect = false;

                if (helpBoard[row][column] == 0) {
                    helpBoard[row][column] = random.nextInt(squareSize) + 1;
                    int rand = helpBoard[row][column];

                    do {
                        if (isAllowed(row, column, rand)) {
                            board[row][column] = rand;
                            isCorrect = true;
                            break;
                        }
                        rand = rand % squareSize + 1;
                    } while (rand != helpBoard[row][column]);
                } else {
                    int rand = board[row][column] % squareSize + 1;
                    board[row][column] = 0;

                    while (rand != helpBoard[row][column]) {
                        if (isAllowed(row, column, rand)) {
                            board[row][column] = rand;
                            isCorrect = true;
                            break;
                        }
                        rand = rand % squareSize + 1;
                    }
                }

                if (!isCorrect) {
                    helpBoard[row][column] = 0;
                    board[row][column] = 0;
                    column = (column - 2) % squareSize;
                    if (column < 0) {
                        column += squareSize;
                        row = Math.max(row - 1, 0);
                    }
                }
            }
        }
    }

    /**
     * Prints sudoku board using System.out.print().
     */
    public final void printSudokuBoard() {
        for (int[] row : board) {
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
        System.out.println();
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
}

