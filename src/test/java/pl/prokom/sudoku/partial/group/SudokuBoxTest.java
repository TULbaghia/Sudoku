package pl.prokom.sudoku.partial.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partial.field.SudokuField;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoxTest {
    SudokuField[] sudokuFields;
    SudokuBox sudokuBox;

    @BeforeEach
    void setUp() {
        AtomicInteger index = new AtomicInteger(1);
        sudokuFields = new SudokuField[9];
        sudokuFields = Arrays.stream(sudokuFields)
                .map(x -> new SudokuField(index.getAndIncrement()))
                .toArray(SudokuField[]::new);

        sudokuBox = new SudokuBox(sudokuFields);
    }

    /**
     * Case description:
     * - getBox return the same object that was pushed in SudokuBox
     */
    @Test
    void getBoxTestCase() {
        assertArrayEquals(sudokuFields, sudokuBox.getBox());
        assertSame(sudokuFields, sudokuBox.getBox());
        sudokuFields[0].resetValue();

        for (int i = 0; i < sudokuFields.length; i++) {
            assertSame(sudokuFields[i], sudokuBox.getBox()[i]);
        }

        sudokuFields[1].resetValue();
        assertTrue(Arrays.deepEquals(sudokuFields, sudokuBox.getBox()));
    }

    /**
     * Case description:
     * - getBox2D return the same object that was pushed in SudokuBox
     */
    @Test
    void getBox2DTestCase() {
        SudokuField[][] fields = sudokuBox.getBox2D();
        assertEquals(sudokuFields.length, fields.length * fields[0].length);

        for (int i = 0; i < sudokuFields.length; i++) {
            assertSame(sudokuFields[i], fields[i / 3][i % 3]);
        }
    }

    /**
     * Case description:
     * - toString should contains all variables
     */
    @Test
    void toStringTestCase() {
        String groupToString = sudokuBox.toString();
        assertTrue(groupToString.contains("SudokuBox"));
        assertTrue(groupToString.contains((new SudokuGroup(sudokuFields){}).toString()));
    }
}