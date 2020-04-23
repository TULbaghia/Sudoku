package pl.prokom.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.model.partial.field.SudokuField;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.model.solver.SudokuSolver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {
    SudokuBoard sudokuBoard;
    SudokuSolver<SudokuBoard> sudokuSolver;

    @BeforeEach
    public void setUp() {
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
    public void parametrizedConstructorTestCase() {
        SudokuField[][] sudokuFields = new SudokuField[sudokuBoard.getBoardSize()][sudokuBoard.getBoardSize()];
        sudokuFields[0][1] = new SudokuField(5);
        sudokuFields[1][2] = new SudokuField(6);
        sudokuFields[2][3] = new SudokuField(7);

        List<List<SudokuField>> sudokuList = Arrays.stream(sudokuFields)
                .map(Arrays::asList)
                .collect(Collectors.toList());

        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), sudokuList);

        for (int i = 0; i < sudokuFields.length; i++) {
            List<SudokuField> sudokuColumn = sudokuBoard.getColumn(i).getColumn();
            assertEquals(sudokuFields[i].length, sudokuColumn.size());
            for (int j = 0; j < sudokuFields.length; j++) {
                if(sudokuFields[j][i] != null) {
                    assertEquals(sudokuFields[j][i], sudokuColumn.get(j));
                }
            }
        }
    }

    /**
     * Case description:
     * - columns in groups and fileds are the same objects segregated correctly
     */
    @Test
    @SuppressWarnings("unchecked")
    public void getColumnTestCase() throws NoSuchFieldException, IllegalAccessException {
        List<List<SudokuField>> sudokuFields = (List<List<SudokuField>>) getPrivateField("sudokuFields");

        for (int i = 0; i < sudokuFields.size(); i++) {
            List<SudokuField> sudokuColumn = sudokuBoard.getColumn(i).getColumn();
            assertEquals(sudokuFields.get(i).size(), sudokuColumn.size());
            for (int j = 0; j < sudokuFields.size(); j++) {
                assertSame(sudokuFields.get(j).get(i), sudokuColumn.get(j));
            }
        }
    }

    /**
     * Case description:
     * - rows in groups and fields are the same object segregated correctly.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void getRowTestCase() throws NoSuchFieldException, IllegalAccessException {
        List<List<SudokuField>> sudokuFields = (List<List<SudokuField>>) getPrivateField("sudokuFields");

        for (int i = 0; i < sudokuFields.size(); i++) {
            List<SudokuField> sudokuRow = sudokuBoard.getRow(i).getRow();
            assertEquals(sudokuFields.get(i).size(), sudokuRow.size());
            for (int j = 0; j < sudokuFields.get(i).size(); j++) {
                assertSame(sudokuFields.get(i).get(j), sudokuRow.get(j));
            }
        }
    }

    /**
     * Case description:
     * - boxes in groups and fields are the same object segregated correctly.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void getBoxTestCase() throws NoSuchFieldException, IllegalAccessException {
        List<List<SudokuField>> sudokuFields = (List<List<SudokuField>>) getPrivateField("sudokuFields");

        for (int maxRow = 0; maxRow < sudokuBoard.getMiniBoxDim(); maxRow++) {
            for (int maxCol = 0; maxCol < sudokuBoard.getMiniBoxDim(); maxCol++) {
                SudokuField[][] sudokuBox = sudokuBoard.getBox(maxRow, maxCol).getBox2D();
                for (int minRow = 0; minRow < sudokuBoard.getMiniBoxDim(); minRow++) {
                    for (int minCol = 0; minCol < sudokuBoard.getMiniBoxDim(); minCol++) {
                        assertSame(sudokuBox[minRow][minCol],
                                sudokuFields.get(maxRow * sudokuBoard.getMiniBoxDim() + minRow).get(maxCol * sudokuBoard.getMiniBoxDim() + minCol));
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
    public void getBoardSizeTestCase() {
        assertEquals(sudokuBoard.getMiniBoxDim() * sudokuBoard.getMiniBoxDim(), sudokuBoard.getBoardSize());
    }

    /**
     * Case description:
     * - check if get value is same as the one in board
     */
    @Test
    @SuppressWarnings("unchecked")
    public void getTestCase() throws NoSuchFieldException, IllegalAccessException {
        List<List<SudokuField>> sudokuFields = (List<List<SudokuField>>) getPrivateField("sudokuFields");

        for (int row = 0; row < sudokuFields.size(); row++) {
            for (int col = 0; col < sudokuFields.get(row).size(); col++) {
                assertSame(sudokuFields.get(row).get(col).getFieldValue(), sudokuBoard.get(row, col));
            }
        }
    }

    /**
     * Case description:
     * - check if get value is same as the one in board
     */
    @Test
    public void setTestCase() {
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
    public void resetTestCase() {
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
    @SuppressWarnings("unchecked")
    public void getCopyOfBoardTestCase() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method method = sudokuBoard.getClass().getDeclaredMethod("getCopyOfBoard");
        method.setAccessible(true);

        List<List<SudokuField>> newFields = (List<List<SudokuField>>) method.invoke(sudokuBoard);
        List<List<SudokuField>> oldFields = (List<List<SudokuField>>) getPrivateField("sudokuFields");

        for (int i = 0; i < sudokuBoard.getBoardSize(); i++) {
            for (int j = 0; j < sudokuBoard.getBoardSize(); j++) {
                assertEquals(oldFields.get(i).get(j), newFields.get(i).get(j));
                assertNotSame(oldFields.get(i).get(j), newFields.get(i).get(j));
            }
        }
    }

    /**
     * Case description:
     * - check if get reset field sets its value to 0
     */
    @Test
    public void solveTestCase() {
        assertFalse(sudokuBoard.isSolved());
        sudokuBoard.solveGame();
        assertTrue(sudokuBoard.isSolved());
    }

    /**
     * Case description:
     * - toString should contains all variables
     */
    @Test
    @SuppressWarnings("unchecked")
    public void toStringTestCase() throws NoSuchFieldException, IllegalAccessException {
        sudokuBoard = new SudokuBoard(sudokuSolver, null);
        sudokuBoard.solveGame();

        List<List<SudokuField>> sudokuFields = (List<List<SudokuField>>) getPrivateField("sudokuFields");

        String groupToString = sudokuBoard.toString();
        assertTrue(groupToString.contains("SudokuBoard"));
        assertTrue(groupToString.contains("miniBoxDim=" + sudokuBoard.getMiniBoxDim()));
        assertTrue(groupToString.contains("boardSize=" + sudokuBoard.getBoardSize()));
        assertTrue(groupToString.contains("sudokuSolver=" + sudokuSolver.toString()));
        assertTrue(groupToString.contains("sudokuFields=" + sudokuFields.toString()));
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
    public void equalsTestCase() {
        assertEquals(sudokuBoard, sudokuBoard);

        assertNotEquals(sudokuBoard, null);
        assertNotEquals(sudokuBoard, "NE");

        SudokuBoard newSudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(sudokuBoard, newSudokuBoard);

        newSudokuBoard.set(0, 0, 1);
        sudokuBoard.set(0, 0, 2);

        assertNotEquals(sudokuBoard, newSudokuBoard);

        assertNotEquals(sudokuBoard, new SudokuBoard(new BacktrackingSudokuSolver(), null, 4));
    }

    /**
     * Case description:
     * - same object should return same hashcode
     * - object with same settings shoud return same hashCode
     * - object with different settings return different hashCode
     */
    @Test
    public void hashCodeTestCase() {
        assertEquals(sudokuBoard.hashCode(), sudokuBoard.hashCode());

        assertNotEquals(sudokuBoard.hashCode(), "NE".hashCode());

        SudokuBoard newSudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(sudokuBoard.hashCode(), newSudokuBoard.hashCode());

        newSudokuBoard.set(0, 0, 1);
        sudokuBoard.set(0, 0, 2);

        assertNotEquals(sudokuBoard.hashCode(), newSudokuBoard.hashCode());

        assertNotEquals(sudokuBoard.hashCode(), new SudokuBoard(new BacktrackingSudokuSolver(), null, 4).hashCode());
    }

    /**
     * Case description:
     * - cloned object should be equal to self
     * - cloned object is not same as original
     */
    @Test
    @SuppressWarnings("unchecked")
    public void cloneTestCase() throws NoSuchFieldException, IllegalAccessException {
        SudokuBoard clone = sudokuBoard.clone();
        Field field = sudokuBoard.getClass().getDeclaredField("sudokuFields");
        field.setAccessible(true);

        assertEquals(sudokuBoard, clone);
        assertNotSame(sudokuBoard, clone);

        List<List<SudokuField>> fields1 = (List<List<SudokuField>>) field.get(sudokuBoard);
        List<List<SudokuField>> fields2 = (List<List<SudokuField>>) field.get(clone);

        for (int i = 0; i < sudokuBoard.getBoardSize(); i++) {
            assertNotSame(sudokuBoard.getColumn(i), clone.getColumn(i));
            assertNotSame(sudokuBoard.getColumn(i), clone.getRow(i));
            for (int j = 0; j < sudokuBoard.getBoardSize(); j++) {
                assertEquals(fields1.get(i).get(j), fields2.get(i).get(j));
                assertNotSame(fields1.get(i).get(j), fields2.get(i).get(j));
            }
        }
    }

}