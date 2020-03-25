package pl.prokom.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class BacktrackingSudokuSolverTest {

    private SudokuBoard sudokuBoard;
    private BacktrackingSudokuSolver solver;

    @BeforeEach
    void setUp() {
        sudokuBoard = new SudokuBoard();
        solver = new BacktrackingSudokuSolver();
        solver.solve(sudokuBoard);
    }

    /**
     * Check, wether board is square
     *
     * @param board gameboard from sudoku object
     */
    private void checkSquare(int[][] board) {
        for (int[] ints : board) {
            assertEquals(ints.length, board.length);
        }
    }

    /**
     * Function checks if each fillBoard call generate different board view
     * Case description:
     * - each board should be squared
     * - each fillBoard should generate different values in board
     */
    @Test
    void sudokuSolveTestCase() {
        solver.solve(sudokuBoard);
        int[][] board1 = sudokuBoard.getCopyOfBoard();
        checkSquare(board1);

        sudokuBoard.setBoard(new int[sudokuBoard.getSquareSize()][sudokuBoard.getSquareSize()]);
        solver.solve(sudokuBoard);
        int[][] board2 = sudokuBoard.getCopyOfBoard();
        checkSquare(board2);

        assertEquals(board1.length, board2.length);
        for (int i = 0; i < board1.length; i++) {
            assertEquals(board1[i].length, board2[i].length);
        }

        assertFalse(Arrays.deepEquals(board1, board2));
        System.out.println(sudokuBoard.toString());

        SudokuBoard sudokuBoard2 = new SudokuBoard();
        solver.solve(sudokuBoard2);
        assertNotEquals(sudokuBoard2, sudokuBoard);
    }

    /**
     * Function checks if values are within correct range
     * Case description:
     * - each row should have n (board.length) values [1..n]
     * there is no possibility to add other values
     */
    @Test
    void sudokuAllowedValuesTestCase() {
        sudokuBoard.setBoard(new int[sudokuBoard.getSquareSize()][sudokuBoard.getSquareSize()]);

        for (int num = -5; num < sudokuBoard.getSquareSize() + 5; num++) {
            assertEquals(0 < num && sudokuBoard.getSquareSize() >= num
                    , solver.isAllowed(1, 1, num, sudokuBoard));
        }
    }

    /**
     * Function checks uniqueness of values in each row
     * Case description:
     * - board should be squared
     * - used SortedSet to store only unique values from each row
     * - during push to SS function returns true when there is no duplicates in set
     * - size of SS and board's row should be equal
     * - each row should have n (board.length) values [1..n]
     */
    @Test
    void sudokuRowUniqTestCase() {
        int[][] board = sudokuBoard.getCopyOfBoard();
        checkSquare(board);

        for (int i = 0; i < board.length; i++) {
            SortedSet<Integer> uniqueValues = new TreeSet<>();
            for (int j = 0; j < board[0].length; j++) {
                assertTrue(uniqueValues.add(board[i][j]));
            }
            assertEquals(uniqueValues.size(), board.length);
            int actual = 1;
            for (int expected : uniqueValues) {
                assertEquals(expected, actual++);
            }
        }
    }

    /**
     * Function checks uniqueness of values in each column
     * Case description:
     * - board should be squared
     * - used SortedSet to store only unique values from each column
     * - during push to SS function returns true when there is no duplicates in set
     * - size of SS and board's column should be equal
     * - each column should have n (board.length) values [1..n]
     */
    @Test
    void sudokuColumnUniqTestCase() {
        int[][] board = sudokuBoard.getCopyOfBoard();
        checkSquare(board);

        for (int i = 0; i < board[0].length; i++) {
            SortedSet<Integer> uniqueValues = new TreeSet<>();
            for (int j = 0; j < board.length; j++) {
                assertTrue(uniqueValues.add(board[j][i]));
            }
            assertEquals(uniqueValues.size(), board.length);
            int actual = 1;
            for (int expected : uniqueValues) {
                assertEquals(expected, actual++);
            }
        }
    }


    /**
     * Function checks uniqueness of values in each miniBoard
     * Case description:
     * - board should be squared
     * - used SortedSet to store only unique values from each miniBoard
     * - during push to SS function returns true when there is no duplicates in set
     * - size of SS and miniBoard should be equal
     * - each miniBoard should have n (miniBoard.length**) values [1..n]
     */
    @Test
    void sudokuMiniSquareUniqTestCase() {
        int[][] board = sudokuBoard.getCopyOfBoard();
        checkSquare(board);
        int subBoardSize = (int) Math.sqrt(board.length);

        for (int i = 0; i < board.length; i += subBoardSize) {
            for (int j = 0; j < board.length; j += subBoardSize) {
                SortedSet<Integer> uniqueValues = new TreeSet<>();
                for (int k = 0; k < subBoardSize; k++) {
                    for (int l = 0; l < subBoardSize; l++) {
                        assertTrue(uniqueValues.add(board[i + k][j + l]));
                    }
                }
                assertEquals(uniqueValues.size(), subBoardSize * subBoardSize);
                int actual = 1;
                for (int expected : uniqueValues) {
                    assertEquals(expected, actual++);
                }
            }
        }
    }


}
