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

    /**
     * Case description:
     * - object should be equal to self,
     * - object should be equal to created with same parameters
     */
    @Test
    void equalsTestCase() {
        assertEquals(sudokuBox, sudokuBox);

        assertNotEquals(sudokuBox, null);
        assertNotEquals(sudokuBox, new SudokuColumn(sudokuFields));
        assertNotEquals(sudokuBox, new SudokuGroup(sudokuFields) {});

        assertEquals(sudokuBox, new SudokuBox(sudokuFields));

        SudokuField[] sudokuFields = Arrays.stream(this.sudokuFields).map(SudokuField::clone).toArray(SudokuField[]::new);
        for(int i=0; i<sudokuFields.length; i++) {
            assertNotSame(sudokuFields[i], this.sudokuFields[i]);
        }
        sudokuFields[0] = new SudokuField();

        assertNotEquals(sudokuBox, new SudokuBox(sudokuFields));
    }

    /**
     * Case description:
     * - same object should return same hashcode
     * - object with same settings shoud return same hashCode
     * - object with different settings return different hashCode
     */
    @Test
    void hashCodeTestCase() {
        assertEquals(sudokuBox.hashCode(), sudokuBox.hashCode());

        assertNotEquals(sudokuBox.hashCode(), new SudokuColumn(sudokuFields).hashCode());
        assertNotEquals(sudokuBox.hashCode(), new SudokuGroup(sudokuFields) {}.hashCode());

        assertEquals(sudokuBox.hashCode(), new SudokuBox(sudokuFields).hashCode());

        SudokuField[] sudokuFields = Arrays.stream(this.sudokuFields).map(SudokuField::clone).toArray(SudokuField[]::new);
        for(int i=0; i<sudokuFields.length; i++) {
            assertNotSame(sudokuFields[i], this.sudokuFields[i]);
        }
        sudokuFields[0] = new SudokuField();

        assertNotEquals(sudokuBox.hashCode(), new SudokuBox(sudokuFields).hashCode());
    }

    /**
     * Case description:
     * - cloned object should be equal to self
     * - clonned object is not same as original
     */
    @Test
    void cloneTestCase() {
        SudokuBox sudokuBox = this.sudokuBox.clone();
        assertEquals(this.sudokuBox.getClass().getName(), sudokuBox.getClass().getName());
        assertEquals(this.sudokuBox, sudokuBox);
        assertNotSame(this.sudokuBox, sudokuBox);

        SudokuColumn sudokuColumn = new SudokuColumn(this.sudokuBox.clone().getSudokuFields());
        assertNotEquals(sudokuColumn.getClass().getName(), sudokuBox.getClass().getName());
        assertNotEquals(sudokuColumn, sudokuBox);
        assertNotSame(sudokuColumn, sudokuBox);
    }
}