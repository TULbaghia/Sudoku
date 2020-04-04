package pl.prokom.sudoku.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partial.field.SudokuField;
import pl.prokom.sudoku.solver.BacktrackingSudokuSolver;
import pl.prokom.sudoku.solver.SudokuSolver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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
     * - method returns copy of board with new references
     */
    @Test
    void getCopyOfBoardTestCase() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method method = sudokuBoard.getClass().getDeclaredMethod("getCopyOfBoard");
        method.setAccessible(true);

        Field field = sudokuBoard.getClass().getDeclaredField("sudokuFields");
        field.setAccessible(true);

        SudokuField[][] newFields = (SudokuField[][]) method.invoke(sudokuBoard);
        SudokuField[][] oldFields = (SudokuField[][]) field.get(sudokuBoard);

        for (int i = 0; i < sudokuBoard.getBoardSize(); i++) {
            for (int j = 0; j < sudokuBoard.getBoardSize(); j++) {
                assertEquals(oldFields[i][j], newFields[i][j]);
                assertNotSame(oldFields[i][j], newFields[i][j]);
            }
        }
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

    /**
     * Case description:
     * - toString should contains all variables
     */
    @Test
    void toStringTestCase() {
        SudokuField[][] sudokuFields = new SudokuField[9][9];
        sudokuBoard = new SudokuBoard(sudokuSolver, sudokuFields);
        sudokuBoard.solveGame();

        String groupToString = sudokuBoard.toString();
        assertTrue(groupToString.contains("SudokuBoard"));
        assertTrue(groupToString.contains("miniBoxSize=" + sudokuBoard.getMiniBoxSize()));
        assertTrue(groupToString.contains("miniBoxCount=" + sudokuBoard.getMiniBoxCount()));
        assertTrue(groupToString.contains("boardSize=" + sudokuBoard.getBoardSize()));
        assertTrue(groupToString.contains("sudokuSolver=" + sudokuSolver.toString()));
        assertTrue(groupToString.contains("sudokuFields=" + Arrays.deepToString(sudokuFields)));
        for (int i = 0; i < sudokuBoard.getBoardSize(); i++) {
            assertTrue(groupToString.contains(sudokuBoard.getRow(i).toString()));
            assertTrue(groupToString.contains(sudokuBoard.getColumn(i).toString()));
            assertTrue(groupToString.contains(sudokuBoard.getBox(i / 3, i % 3).toString()));
        }

    }

    /**
     * Case description:
     * - object should be equal to self,
     * - object should be equal to created with same parameters
     */
    @Test
    void equalsTestCase() {
        assertEquals(sudokuBoard, sudokuBoard);

        assertNotEquals(sudokuBoard, null);
        assertNotEquals(sudokuBoard, "NE");

        SudokuBoard newSudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(sudokuBoard, newSudokuBoard);

        newSudokuBoard.set(0, 0, 1);
        sudokuBoard.set(0, 0, 2);

        assertNotEquals(sudokuBoard, newSudokuBoard);

        assertNotEquals(sudokuBoard, new SudokuBoard(new BacktrackingSudokuSolver(), null, 4, 4));
    }

    /**
     * Case description:
     * - same object should return same hashcode
     * - object with same settings shoud return same hashCode
     * - object with different settings return different hashCode
     */
    @Test
    void hashCodeTestCase() {
        assertEquals(sudokuBoard.hashCode(), sudokuBoard.hashCode());

        assertNotEquals(sudokuBoard.hashCode(), "NE".hashCode());

        SudokuBoard newSudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(sudokuBoard.hashCode(), newSudokuBoard.hashCode());

        newSudokuBoard.set(0, 0, 1);
        sudokuBoard.set(0, 0, 2);

        assertNotEquals(sudokuBoard.hashCode(), newSudokuBoard.hashCode());

        assertNotEquals(sudokuBoard.hashCode(), new SudokuBoard(new BacktrackingSudokuSolver(), null, 4, 4).hashCode());
    }

    /**
     * Case description:
     * - cloned object should be equal to self
     * - cloned object is not same as original
     */
    @Test
    void cloneTestCase() throws NoSuchFieldException, IllegalAccessException {
        SudokuBoard clone = sudokuBoard.clone();
        Field field = sudokuBoard.getClass().getDeclaredField("sudokuFields");
        field.setAccessible(true);


        assertEquals(sudokuBoard, clone);
        assertNotSame(sudokuBoard, clone);

        SudokuField[][] fields1 = (SudokuField[][]) field.get(sudokuBoard);
        SudokuField[][] fields2 = (SudokuField[][]) field.get(clone);


        for (int i = 0; i < sudokuBoard.getBoardSize(); i++) {
            assertNotSame(sudokuBoard.getColumn(i), clone.getColumn(i));
            assertNotSame(sudokuBoard.getColumn(i), clone.getRow(i));
            for (int j = 0; j < sudokuBoard.getBoardSize(); j++) {
                assertEquals(fields1[i][j], fields2[i][j]);
                assertNotSame(fields1[i][j], fields2[i][j]);
            }
        }
    }

}