package pl.prokom.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partials.SudokuBox;
import pl.prokom.sudoku.partials.SudokuColumn;
import pl.prokom.sudoku.partials.SudokuField;
import pl.prokom.sudoku.partials.SudokuRow;

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
        SudokuField[][] board1 = sudokuBoard.getCopyOfBoard();
        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board1[i].length; j++) {
                board1[i][j].setFieldValue((i * board1.length + j + 3) % sudokuBoard.getSquareSize() + 1);
            }
        }

        SudokuField[][] board2 = sudokuBoard.getCopyOfBoard();

        assertEquals(board1.length, board2.length);
        assertFalse(Arrays.deepEquals(board1, board2));
    }

    /**
     * Case description:
     * - each clone should not reference to origin object (notSame = true)
     */
    @Test
    void sudokuCloneNotSame() throws CloneNotSupportedException {
        SudokuBoard cloneBoard = (SudokuBoard) sudokuBoard.clone();
        assertNotSame(cloneBoard, sudokuBoard);
    }

    /**
     * Case description:
     * - other object type should not be equal to SudokuBoard
     */
    @Test
    void sudokuDifferentObjectTestCase() {
        assertNotEquals(sudokuBoard, null);
        assertNotEquals(sudokuBoard, new BacktrackingSudokuSolver());

    }

    /**
     * Case description:
     * - each cloned object should be equal to origin object (should have same value of all fields)
     * - origin object is self equal
     */
    @Test
    void sudokuEquals() throws CloneNotSupportedException {
        SudokuBoard cloneBoard = (SudokuBoard) sudokuBoard.clone();
        assertEquals(cloneBoard, sudokuBoard);
        assertEquals(sudokuBoard, sudokuBoard);

    }

    /**
     * Case description:
     * - specific object of SudokuBoard class should has another hashcode
     */
    @Test
    void sudokuBoardDifferentHashcode() throws CloneNotSupportedException {
        SudokuBoard cloneBoard = (SudokuBoard) sudokuBoard.clone();
        assertFalse(sudokuBoard.hashCode() != cloneBoard.hashCode());
    }

    /**
     * Case description:
     * - checks if sudoku output board is all correct after being filled by numbers
     */
    @Test
    void sudokuSolveGameTest() {
        assertTrue(sudokuBoard.isSolvedGame());
    }

    /**
     * Case description:
     * - checks if get return correct values from field
     */
    @Test
    void getTestCase() {
        sudokuBoard.setBoard(new SudokuField[SudokuBoard.getSquareSize()][SudokuBoard.getSquareSize()]);
        sudokuBoard.setBoardCell(1, 1, 3);
        assertEquals(3, sudokuBoard.get(1, 1));
    }

    /**
     * Case description:
     * - checks if set- sets correct value in field
     */
    @Test
    void setTestCase() {
        sudokuBoard.setBoard(new SudokuField[SudokuBoard.getSquareSize()][SudokuBoard.getSquareSize()]);
        sudokuBoard.set(1, 1, 3);
        assertEquals(3, sudokuBoard.get(1, 1));
    }

    /**
     * Case description:
     * - checks whether row returned from board is equal
     */
    @Test
    void getRowTestCase() {
        SudokuRow sudokuRow = sudokuBoard.getRow(0);

        for (int i = 0; i < sudokuBoard.getSquareSize(); i++) {
            assertEquals(sudokuRow.getRow()[i], sudokuBoard.getBoardCell(0, i));
        }
    }

    /**
     * Case description:
     * - checks whether column returned from board is equal
     */
    @Test
    void getColumnTestCase() {
        SudokuColumn sudokuColumn = sudokuBoard.getColumn(0);

        for (int i = 0; i < sudokuBoard.getSquareSize(); i++) {
            assertEquals(sudokuColumn.getColumn()[i], sudokuBoard.getBoardCell(i, 0));
        }
    }

    /**
     * Case description:
     * - checks whether box returned from board is equal
     */
    @Test
    void getBoxTestCase() {
        SudokuBox sudokuBox = sudokuBoard.getBox(0, 0);

        for (int i = 0; i < sudokuBoard.getMiniSquareSize(); i++) {
            for (int j = 0; j < sudokuBoard.getMiniSquareSize(); j++) {
                assertEquals(sudokuBox.getBox()[i * sudokuBoard.getMiniSquareSize() + j],
                        sudokuBoard.getBoardCell(i, j));
            }
        }
    }

    /**
     * Case description:
     * - checks whether checkBoard returns correct states
     */
    @Test
    void checkBoardRowTestCase() {
        assertTrue(sudokuBoard.isSolvedGame());
        int value1 = sudokuBoard.get(0, 0);
        int value2 = sudokuBoard.get(0, 1);
        sudokuBoard.set(0, 0, value2);
        sudokuBoard.set(0, 1, value1);
        assertFalse(sudokuBoard.isSolvedGame());
    }

    /**
     * Case description:
     * - checks whether checkBoard returns correct states
     */
    @Test
    void checkBoardColumnTestCase() {
        assertTrue(sudokuBoard.isSolvedGame());
        int value1 = sudokuBoard.get(0, 0);
        int value2 = sudokuBoard.get(1, 0);
        sudokuBoard.set(0, 0, value2);
        sudokuBoard.set(1, 0, value1);
        assertFalse(sudokuBoard.isSolvedGame());
    }

    /**
     * Case description:
     * - checks whether checkBoard returns correct states
     */
    @Test
    void checkBoardBoxTestCase() {
        assertTrue(sudokuBoard.isSolvedGame());
        int value1 = sudokuBoard.get(0, 0);
        sudokuBoard.set(1, 0, value1);
        assertFalse(sudokuBoard.isSolvedGame());
    }

}