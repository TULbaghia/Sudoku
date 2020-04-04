package pl.prokom.sudoku.partial.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partial.field.SudokuField;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {
    SudokuField[] sudokuFields;
    SudokuRow sudokuRow;

    @BeforeEach
    void setUp() {
        AtomicInteger index = new AtomicInteger(1);
        sudokuFields = new SudokuField[9];
        sudokuFields = Arrays.stream(sudokuFields)
                .map(x -> new SudokuField(index.getAndIncrement()))
                .toArray(SudokuField[]::new);

        sudokuRow = new SudokuRow(sudokuFields);
    }

    /**
     * Case description:
     * - getRow return the same object that was pushed in SudokuRow
     */
    @Test
    void getRowTestCase() {
        assertArrayEquals(sudokuFields, sudokuRow.getRow());
        assertSame(sudokuFields, sudokuRow.getRow());
        sudokuFields[0].resetValue();

        for (int i = 0; i < sudokuFields.length; i++) {
            assertSame(sudokuFields[i], sudokuRow.getRow()[i]);
        }

        sudokuFields[1].resetValue();
        assertTrue(Arrays.deepEquals(sudokuFields, sudokuRow.getRow()));
    }

    /**
     * Case description:
     * - toString should contains all variables
     */
    @Test
    void toStringTestCase() {
        String groupToString = sudokuRow.toString();
        assertTrue(groupToString.contains("SudokuRow"));
        assertTrue(groupToString.contains((new SudokuGroup(sudokuFields){}).toString()));
    }

    /**
     * Case description:
     * - object should be equal to self,
     * - object should be equal to created with same parameters
     */
    @Test
    void equalsTestCase() {
        assertEquals(sudokuRow, sudokuRow);

        assertNotEquals(sudokuRow, null);
        assertNotEquals(sudokuRow, new SudokuColumn(sudokuFields));
        assertNotEquals(sudokuRow, new SudokuGroup(sudokuFields) {});

        assertEquals(sudokuRow, new SudokuRow(sudokuFields));

        SudokuField[] sudokuFields = Arrays.stream(this.sudokuFields).map(SudokuField::clone).toArray(SudokuField[]::new);
        for(int i=0; i<sudokuFields.length; i++) {
            assertNotSame(sudokuFields[i], this.sudokuFields[i]);
        }
        sudokuFields[0] = new SudokuField();

        assertNotEquals(sudokuRow, new SudokuRow(sudokuFields));
    }

    /**
     * Case description:
     * - same object should return same hashcode
     * - object with same settings shoud return same hashCode
     * - object with different settings return different hashCode
     */
    @Test
    void hashCodeTestCase() {
        assertEquals(sudokuRow.hashCode(), sudokuRow.hashCode());

        assertNotEquals(sudokuRow.hashCode(), new SudokuColumn(sudokuFields).hashCode());
        assertNotEquals(sudokuRow.hashCode(), new SudokuGroup(sudokuFields) {}.hashCode());

        assertEquals(sudokuRow.hashCode(), new SudokuRow(sudokuFields).hashCode());

        SudokuField[] sudokuFields = Arrays.stream(this.sudokuFields).map(SudokuField::clone).toArray(SudokuField[]::new);
        for(int i=0; i<sudokuFields.length; i++) {
            assertNotSame(sudokuFields[i], this.sudokuFields[i]);
        }
        sudokuFields[0] = new SudokuField();

        assertNotEquals(sudokuRow.hashCode(), new SudokuRow(sudokuFields).hashCode());
    }

    /**
     * Case description:
     * - cloned object should be equal to self
     * - clonned object is not same as original
     */
    @Test
    void cloneTestCase() {
        SudokuRow sudokuRow = this.sudokuRow.clone();
        assertEquals(this.sudokuRow.getClass().getName(), sudokuRow.getClass().getName());
        assertEquals(this.sudokuRow, sudokuRow);
        assertNotSame(this.sudokuRow, sudokuRow);

        SudokuColumn sudokuColumn = new SudokuColumn(this.sudokuRow.clone().getSudokuFields());
        assertNotEquals(sudokuRow.getClass().getName(), sudokuColumn.getClass().getName());
        assertNotEquals(sudokuRow, sudokuColumn);
        assertNotSame(sudokuRow, sudokuColumn);
    }
}