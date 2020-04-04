package pl.prokom.sudoku.partial.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.exception.IllegalFieldValueException;
import pl.prokom.sudoku.exception.IllegalPropertyChangeEventSourceException;
import pl.prokom.sudoku.partial.field.SudokuField;

import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class SudokuGroupTest {
    SudokuField[] sudokuFields;
    SudokuGroup sudokuGroup;

    @BeforeEach
    void setUp() {
        AtomicInteger index = new AtomicInteger(1);
        sudokuFields = new SudokuField[9];
        sudokuFields = Arrays.stream(sudokuFields)
                .map(x -> new SudokuField(index.getAndIncrement()))
                .toArray(SudokuField[]::new);

        sudokuGroup = new SudokuGroup(sudokuFields) {
        };
    }

    /**
     * Case description:
     * - getColumn return the same object that was pushed in SudokuColumn
     */
    @Test
    void getColumnTestCase() {
        assertArrayEquals(sudokuFields, sudokuGroup.getSudokuFields());
        assertSame(sudokuFields, sudokuGroup.getSudokuFields());
        sudokuFields[0].resetValue();

        for (int i = 0; i < sudokuFields.length; i++) {
            assertSame(sudokuFields[i], sudokuGroup.getSudokuFields()[i]);
        }

        sudokuFields[1].resetValue();
        assertTrue(Arrays.deepEquals(sudokuFields, sudokuGroup.getSudokuFields()));
    }

    /**
     * Case description:
     * - same values in columnFields should return false
     */
    @Test
    void verifySameTestCase() {
        assertTrue(sudokuGroup.verify());

        AtomicInteger index = new AtomicInteger(1);
        sudokuFields = Arrays.stream(sudokuFields)
                .map(x -> new SudokuField(index.getAndIncrement()))
                .toArray(SudokuField[]::new);

        sudokuFields[0] = new SudokuField(2);

        sudokuGroup = new SudokuGroup(sudokuFields) {
        };

        assertFalse(sudokuGroup.verify());

    }

    /**
     * Case description:
     * - checks if values are unique
     */
    @Test
    void verifyTestCase() {
        assertTrue(sudokuGroup.verify());

        sudokuGroup.getSudokuFields()[0].resetValue();
        assertFalse(sudokuGroup.verify());

        sudokuGroup.getSudokuFields()[0].setFieldValue(1);
        assertTrue(sudokuGroup.verify());

        sudokuGroup.getSudokuFields()[sudokuFields.length - 1].resetValue();
        assertFalse(sudokuGroup.verify());
    }

    /**
     * Case description:
     * - throw error when method called from unsupported caller
     */
    @Test
    void propertyChangeTestCase() {
        PropertyChangeEvent pce = new PropertyChangeEvent("", "value", "0", "1");
        assertThrows(IllegalPropertyChangeEventSourceException.class, () -> sudokuGroup.propertyChange(pce));
    }

    /**
     * Case description:
     * - allows to add legal value to field
     */
    @Test
    void isAllowedToSetTestCase() {
        sudokuFields = Arrays.stream(sudokuFields)
                .map(x -> new SudokuField())
                .toArray(SudokuField[]::new);
        sudokuGroup = new SudokuGroup(sudokuFields) {
        };

        assertDoesNotThrow(() -> sudokuGroup.getSudokuFields()[0].setFieldValue(5));

        assertThrows(IllegalFieldValueException.class, () -> sudokuGroup.getSudokuFields()[1].setFieldValue(5));

        assertThrows(IllegalFieldValueException.class, () -> sudokuGroup.getSudokuFields()[0].setFieldValue(10));
    }

    /**
     * Case description:
     * - setting equal value shoud not throw exception as of used listener property:
     * _    - In case the value set is same as that of the previous one, the event is not fired
     */
    @Test
    void isAllowedToSetEqualValueTestCase() {
        assertEquals(1, sudokuGroup.getSudokuFields()[0].getFieldValue());

        sudokuGroup.getSudokuFields()[0].setFieldValue(1);
        assertEquals(1, sudokuGroup.getSudokuFields()[0].getFieldValue());
    }

    /**
     * Case description:
     * - toString should contains all variables
     */
    @Test
    void toStringTestCase() {
        String groupToString = sudokuGroup.toString();
        assertTrue(groupToString.contains("SudokuGroup"));
        for (SudokuField sudokuField : sudokuFields) {
            assertTrue(groupToString.contains(sudokuField.toString()));
        }
    }

    /**
     * Case description:
     * - object should be equal to self,
     * - object should be equal to created with same parameters
     * - object should be equal to one create with different array
     * - object should not be equal when values are different
     * - object should not be equal when array length is different
     */
    @Test
    void equalsTestCase() {
        assertEquals(sudokuGroup, sudokuGroup);

        assertNotEquals(sudokuGroup, "");
        assertEquals(sudokuGroup, new SudokuGroup(sudokuFields) {});

        SudokuField[] sudokuFields = Arrays.stream(this.sudokuFields).map(SudokuField::clone).toArray(SudokuField[]::new);
        for(int i=0; i<sudokuFields.length; i++) {
            assertNotSame(sudokuFields[i], this.sudokuFields[i]);
        }
        assertEquals(sudokuGroup, new SudokuGroup(sudokuFields) {});

        sudokuFields[0] = new SudokuField(5);
        assertNotEquals(sudokuGroup, new SudokuGroup(sudokuFields) {});

        assertNotEquals(sudokuGroup, new SudokuGroup(new SudokuField[]{new SudokuField(4)}) {});
    }

    /**
     * Case description:
     * - same object should return same hashcode
     * - object with same settings shoud return same hashCode
     * - object with different settings return different hashCode
     */
    @Test
    void hashCodeTestCase() {
        assertEquals(sudokuGroup.hashCode(), sudokuGroup.hashCode());

        assertEquals(sudokuGroup.hashCode(), new SudokuGroup(sudokuFields) {}.hashCode());

        SudokuField[] sudokuFields = Arrays.stream(this.sudokuFields).map(SudokuField::clone).toArray(SudokuField[]::new);
        for(int i=0; i<sudokuFields.length; i++) {
            assertNotSame(sudokuFields[i], this.sudokuFields[i]);
        }
        assertEquals(sudokuGroup.hashCode(), new SudokuGroup(sudokuFields) {}.hashCode());

        sudokuFields[0] = new SudokuField(5);
        assertNotEquals(sudokuGroup.hashCode(), new SudokuGroup(sudokuFields) {}.hashCode());

        assertNotEquals(sudokuGroup.hashCode(), new SudokuGroup(new SudokuField[]{new SudokuField(4)}) {}.hashCode());
    }

    /**
     * Case description:
     * - cloned object should be equal to self
     * - clonned object is not same as original
     */
    @Test
    void cloneTestCase() {
        SudokuGroup sudokuGroup = this.sudokuGroup.clone();
        assertEquals(this.sudokuGroup, sudokuGroup);
        assertNotSame(this.sudokuGroup, sudokuGroup);
        for(int i=0; i<sudokuGroup.getSudokuFields().length; i++) {
            assertNotSame(this.sudokuGroup.getSudokuFields()[i], sudokuGroup.getSudokuFields()[i]);
        }

        int tmp = sudokuGroup.getSudokuFields()[0].getFieldValue();
        sudokuGroup.getSudokuFields()[0].resetValue();
        assertNotEquals(this.sudokuGroup, sudokuGroup);

        assertDoesNotThrow(() -> sudokuGroup.getSudokuFields()[0].setFieldValue(tmp));
        assertThrows(IllegalFieldValueException.class, () -> sudokuGroup.getSudokuFields()[0].setFieldValue(-1));
    }


}