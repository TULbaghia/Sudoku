package pl.prokom.sudoku.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partial.field.SudokuField;
import pl.prokom.sudoku.solver.BacktrackingSudokuSolver;
import pl.prokom.sudoku.solver.SudokuSolver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {
    SudokuBoard sudokuBoard;
    SudokuSolver<SudokuBoard> sudokuSolver;

    @BeforeEach
    void setUp() {
        sudokuSolver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(sudokuSolver);
    }

    private Object getPrivateField(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = sudokuBoard.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(sudokuBoard);
    }

    /**
     * Case description:
     * - parametrized Constructor does not override given board
     */
    @Test
    void parametrizedConstructorTestCase() {
        SudokuField[][] sudokuFields = new SudokuField[sudokuBoard.getBoardSize()][];
        sudokuFields[0] = new SudokuField[]{new SudokuField(), null, null, null, null, null, null, null, null};
        sudokuFields[1] = new SudokuField[]{null, new SudokuField(), null, null, null, null, null, null, null};
        sudokuFields[2] = new SudokuField[]{null, null, new SudokuField(), null, null, null, null, null, null};

        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), sudokuFields);

        for (int i = 0; i < sudokuFields.length; i++) {
            SudokuField[] sudokuColumn = sudokuBoard.getColumn(i).getColumn();
            assertEquals(sudokuFields[i].length, sudokuColumn.length);
            for (int j = 0; j < sudokuFields.length; j++) {
                assertSame(sudokuFields[j][i], sudokuColumn[j]);
            }
        }
    }

    /**
     * Case description:
     * - columns in groups and fileds are the same objects segregated correctly
     */
    @Test
    void getColumnTestCase() throws NoSuchFieldException, IllegalAccessException {
        SudokuField[][] sudokuFields = (SudokuField[][]) getPrivateField("sudokuFields");

        for (int i = 0; i < sudokuFields.length; i++) {
            SudokuField[] sudokuColumn = sudokuBoard.getColumn(i).getColumn();
            assertEquals(sudokuFields[i].length, sudokuColumn.length);
            for (int j = 0; j < sudokuFields.length; j++) {
                assertSame(sudokuFields[j][i], sudokuColumn[j]);
            }
        }
    }

    /**
     * Case description:
     * - rows in groups and fields are the same object segregated correctly.
     */
    @Test
    void getRowTestCase() throws NoSuchFieldException, IllegalAccessException {
        SudokuField[][] sudokuFields = (SudokuField[][]) getPrivateField("sudokuFields");

        for (int i = 0; i < sudokuFields.length; i++) {
            SudokuField[] sudokuRow = sudokuBoard.getRow(i).getRow();
            assertEquals(sudokuFields[i].length, sudokuRow.length);
            for (int j = 0; j < sudokuFields[i].length; j++) {
                assertSame(sudokuFields[i][j], sudokuRow[j]);
            }
        }
    }

    /**
     * Case description:
     * - boxes in groups and fields are the same object segregated correctly.
     */
    @Test
    void getBoxTestCase() throws NoSuchFieldException, IllegalAccessException {
        SudokuField[][] sudokuFields = (SudokuField[][]) getPrivateField("sudokuFields");

        for (int maxRow = 0; maxRow < sudokuBoard.getMiniBoxCount(); maxRow++) {
            for (int maxCol = 0; maxCol < sudokuBoard.getMiniBoxCount(); maxCol++) {
                SudokuField[][] sudokuBox = sudokuBoard.getBox(maxRow, maxCol).getBox2D();
                for (int minRow = 0; minRow < sudokuBoard.getMiniBoxSize(); minRow++) {
                    for (int minCol = 0; minCol < sudokuBoard.getMiniBoxSize(); minCol++) {
                        assertSame(sudokuBox[minRow][minCol],
                                sudokuFields[maxRow * sudokuBoard.getMiniBoxSize() + minRow][maxCol * sudokuBoard.getMiniBoxSize() + minCol]);
                    }
                }
            }
        }
    }

    /**
     * Case description:
     * - check if getter of boardSize works correctly
     */
    @Test
    void getBoardSizeTestCase() {
        assertEquals(sudokuBoard.getMiniBoxSize() * sudokuBoard.getMiniBoxCount(), sudokuBoard.getBoardSize());
    }

    /**
     * Case description:
     * - check if get value is same as the one in board
     */
    @Test
    void getTestCase() throws NoSuchFieldException, IllegalAccessException {
        SudokuField[][] sudokuFields = (SudokuField[][]) getPrivateField("sudokuFields");

        for (int row = 0; row < sudokuFields.length; row++) {
            for (int col = 0; col < sudokuFields[row].length; col++) {
                assertSame(sudokuFields[row][col].getFieldValue(), sudokuBoard.get(row, col));
            }
        }
    }

    /**
     * Case description:
     * - check if get value is same as the one in board
     */
    @Test
    void setTestCase() {
        assertEquals(0, sudokuBoard.get(0, 0));

        sudokuBoard.set(0, 0, 5);
        assertEquals(5, sudokuBoard.get(0, 0));

        sudokuBoard.set(0, 0, 7);
        assertEquals(7, sudokuBoard.get(0, 0));
    }

    /**
     * Case description:
     * - check if get reset field sets its value to 0
     */
    @Test
    void resetTestCase() {
        assertEquals(0, sudokuBoard.get(0, 0));

        sudokuBoard.set(0, 0, 5);
        assertEquals(5, sudokuBoard.get(0, 0));
        sudokuBoard.reset(0, 0);
        assertEquals(0, sudokuBoard.get(0, 0));
    }

    /**
     * Case description:
     * - check if get reset field sets its value to 0
     */
    @Test
    void solveTestCase() {
        assertFalse(sudokuBoard.isSolved());
        sudokuBoard.solveGame();
        assertTrue(sudokuBoard.isSolved());
    }
}