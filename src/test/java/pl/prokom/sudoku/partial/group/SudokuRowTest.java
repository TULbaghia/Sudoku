package pl.prokom.sudoku.partial.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partial.field.SudokuField;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {
    List<SudokuField> sudokuFields;
    SudokuRow sudokuRow;

    @BeforeEach
    void setUp() {
        AtomicInteger index = new AtomicInteger(1);
        sudokuFields = Arrays.asList(Stream.generate(() -> new SudokuField(index.getAndIncrement())).limit(9).toArray(SudokuField[]::new));

        sudokuRow = new SudokuRow(sudokuFields);
    }

    /**
     * Case description:
     * - getRow return the same object that was pushed in SudokuRow
     */
    @Test
    void getRowTestCase() {
        assertEquals(sudokuFields, sudokuRow.getRow());
        assertSame(sudokuFields, sudokuRow.getRow());
        sudokuFields.get(0).resetValue();

        for (int i = 0; i < sudokuFields.size(); i++) {
            assertSame(sudokuFields.get(i), sudokuRow.getRow().get(i));
        }

        sudokuFields.get(1).resetValue();
        assertEquals(sudokuFields, sudokuRow.getRow());
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

        List<SudokuField> sudokuFields = Arrays.asList(this.sudokuFields.stream().map(SudokuField::clone).toArray(SudokuField[]::new));
        for(int i=0; i<sudokuFields.size(); i++) {
            assertNotSame(sudokuFields.get(i), this.sudokuFields.get(i));
        }
        sudokuFields.set(0, new SudokuField());

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

        List<SudokuField> sudokuFields = Arrays.asList(this.sudokuFields.stream().map(SudokuField::clone).toArray(SudokuField[]::new));
        for(int i=0; i<sudokuFields.size(); i++) {
            assertNotSame(sudokuFields.get(i), this.sudokuFields.get(i));
        }
        sudokuFields.set(0, new SudokuField());

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