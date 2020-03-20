package pl.prokom.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    private SudokuBoard sudokuBoard;
    private BacktrackingSudokuSolver solver;

    @BeforeEach
    void setUp() {
        sudokuBoard = new SudokuBoard();
        solver = new BacktrackingSudokuSolver();
        solver.solve(sudokuBoard);
    }

    /**
     * Function checks uniqueness of value returned by copyOfBoard
     * Case description:
     * - getCopyOfBoard should always return the same array
     * - changes in returned array does not affect original array
     */
    @Test
    void sudokuGetCopyOfBoardUniqTestCase() {
        int[][] board1 = sudokuBoard.getCopyOfBoard();
        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board1[i].length; j++) {
                board1[i][j] = i * board1.length + j;
            }
        }

        int[][] board2 = sudokuBoard.getCopyOfBoard();

        assertEquals(board1.length, board2.length);
        assertFalse(Arrays.deepEquals(board1, board2));
    }

    /**
     * Case description:
     * - each clone should not reference to origin object (notSame = true)
     */
    @Test
    void sudokuCloneNotSame() throws CloneNotSupportedException {
        solver.solve(sudokuBoard);
        SudokuBoard cloneBoard = (SudokuBoard) sudokuBoard.clone();
        assertNotSame(cloneBoard, sudokuBoard);
    }

    /**
     * Case description:
     * - each cloned object should be equal to origin object (should have same value of all fields)
     * - origin object is self equal
     */
    @Test
    void sudokuEquals() throws CloneNotSupportedException {
        solver.solve(sudokuBoard);
        SudokuBoard cloneBoard = (SudokuBoard) sudokuBoard.clone();
        assertTrue(cloneBoard.equals(sudokuBoard));
        assertTrue(sudokuBoard.equals(sudokuBoard));

    }

    /**
     * Case description:
     * - specific object of SudokuBoard class should has another hashcode
     */
    @Test
    void sudokuBoardDifferentHashcode() throws CloneNotSupportedException {
        solver.solve(sudokuBoard);
        SudokuBoard cloneBoard = (SudokuBoard) sudokuBoard.clone();
        assertFalse(sudokuBoard.hashCode() != cloneBoard.hashCode());
    }

    /**
     * Case description:
     * - checks if sudoku output board is all correct after being filled by numbers
     */
    @Test
    void sudokuSolveGameTest() {
        solver.solve(sudokuBoard);
        assertTrue(sudokuBoard.solveGame());
    }

}