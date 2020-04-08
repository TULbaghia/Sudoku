package pl.prokom.sudoku.partial.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partial.field.SudokuField;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {
    List<SudokuField> sudokuFields;
    SudokuColumn sudokuColumn;

    @BeforeEach
    void setUp() {
        AtomicInteger index = new AtomicInteger(1);
        sudokuFields = Arrays.asList(Stream.generate(() -> new SudokuField(index.getAndIncrement())).limit(9).toArray(SudokuField[]::new));

        sudokuColumn = new SudokuColumn(sudokuFields);
    }

    /**
     * Case description:
     * - getColumn return the same object that was pushed in SudokuColumn
     */
    @Test
    void getColumnTestCase() {
        assertEquals(sudokuFields, sudokuColumn.getColumn());
        assertSame(sudokuFields, sudokuColumn.getColumn());
        sudokuFields.get(0).resetValue();

        for (int i = 0; i < sudokuFields.size(); i++) {
            assertSame(sudokuFields.get(i), sudokuColumn.getColumn().get(i));
        }

        sudokuFields.get(1).resetValue();
        assertEquals(sudokuFields, sudokuColumn.getColumn());
    }

    /**
     * Case description:
     * - toString should contains all variables
     */
    @Test
    void toStringTestCase() {
        String groupToString = sudokuColumn.toString();
        assertTrue(groupToString.contains("SudokuColumn"));
        assertTrue(groupToString.contains((new SudokuGroup(sudokuFields){}).toString()));
    }

    /**
     * Case description:
     * - object should be equal to self,
     * - object should be equal to created with same parameters
     */
    @Test
    void equalsTestCase() {
        assertEquals(sudokuColumn, sudokuColumn);

        assertNotEquals(sudokuColumn, null);
        assertNotEquals(sudokuColumn, new SudokuRow(sudokuFields));
        assertNotEquals(sudokuColumn, new SudokuGroup(sudokuFields) {});

        assertEquals(sudokuColumn, new SudokuColumn(sudokuFields));

        List<SudokuField> sudokuFields = Arrays.asList(this.sudokuFields.stream().map(SudokuField::clone).toArray(SudokuField[]::new));
        for(int i=0; i<sudokuFields.size(); i++) {
            assertNotSame(sudokuFields.get(i), this.sudokuFields.get(i));
        }
        sudokuFields.set(0, new SudokuField());

        assertNotEquals(sudokuColumn, new SudokuColumn(sudokuFields));
    }

    /**
     * Case description:
     * - same object should return same hashcode
     * - object with same settings shoud return same hashCode
     * - object with different settings return different hashCode
     */
    @Test
    void hashCodeTestCase() {
        assertEquals(sudokuColumn.hashCode(), sudokuColumn.hashCode());

        assertNotEquals(sudokuColumn.hashCode(), new SudokuRow(sudokuFields).hashCode());
        assertNotEquals(sudokuColumn.hashCode(), new SudokuGroup(sudokuFields) {}.hashCode());

        assertEquals(sudokuColumn.hashCode(), new SudokuColumn(sudokuFields).hashCode());

        List<SudokuField> sudokuFields = Arrays.asList(this.sudokuFields.stream().map(SudokuField::clone).toArray(SudokuField[]::new));
        for(int i=0; i<sudokuFields.size(); i++) {
            assertNotSame(sudokuFields.get(i), this.sudokuFields.get(i));
        }
        sudokuFields.set(0, new SudokuField());

        assertNotEquals(sudokuColumn.hashCode(), new SudokuColumn(sudokuFields).hashCode());
    }

    /**
     * Case description:
     * - cloned object should be equal to self
     * - clonned object is not same as original
     */
    @Test
    void cloneTestCase() {
        SudokuColumn sudokuColumn = this.sudokuColumn.clone();
        assertEquals(this.sudokuColumn.getClass().getName(), sudokuColumn.getClass().getName());
        assertEquals(this.sudokuColumn, sudokuColumn);
        assertNotSame(this.sudokuColumn, sudokuColumn);

        SudokuBox sudokuBox = new SudokuBox(this.sudokuColumn.clone().getSudokuFields());
        assertNotEquals(sudokuColumn.getClass().getName(), sudokuBox.getClass().getName());
        assertNotEquals(sudokuColumn, sudokuBox);
        assertNotSame(sudokuColumn, sudokuBox);
    }
}