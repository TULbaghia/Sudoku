package pl.prokom.model.partial.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.model.exception.IllegalFieldValueException;
import pl.prokom.model.exception.IllegalPropertyChangeEventSourceException;
import pl.prokom.model.partial.field.SudokuField;

import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SudokuGroupTest {
    List<SudokuField> sudokuFiels;
    SudokuGroup sudokuGroup;

    @BeforeEach
    void setUp() {
        AtomicInteger index = new AtomicInteger(1);
        sudokuFiels = Arrays.asList(Stream.generate(() -> new SudokuField(index.getAndIncrement())).limit(9).toArray(SudokuField[]::new));

        sudokuGroup = new SudokuGroup(sudokuFiels) {};
    }

    /**
     * Case description:
     * - getColumn return the same object that was pushed in SudokuColumn
     */
    @Test
    void getColumnTestCase() {
        assertEquals(sudokuFiels, sudokuGroup.getSudokuFields());
        assertSame(sudokuFiels, sudokuGroup.getSudokuFields());
        sudokuFiels.get(0).resetValue();

        for (int i = 0; i < sudokuFiels.size(); i++) {
            assertSame(sudokuFiels.get(i), sudokuGroup.getSudokuFields().get(i));
        }

        sudokuFiels.get(1).resetValue();
        assertEquals(sudokuFiels, sudokuGroup.getSudokuFields());
    }

    /**
     * Case description:
     * - same values in columnFields should return false
     */
    @Test
    void verifySameTestCase() {
        assertTrue(sudokuGroup.verify());

        AtomicInteger index = new AtomicInteger(1);
        sudokuFiels = Arrays.asList(Stream.generate(() -> new SudokuField(index.getAndIncrement())).limit(9).toArray(SudokuField[]::new));

        sudokuFiels.set(2, new SudokuField(2));

        sudokuGroup = new SudokuGroup(sudokuFiels) {};

        assertFalse(sudokuGroup.verify());

    }

    /**
     * Case description:
     * - checks if values are unique
     */
    @Test
    void verifyTestCase() {
        assertTrue(sudokuGroup.verify());

        sudokuGroup.getSudokuFields().get(0).resetValue();
        assertFalse(sudokuGroup.verify());

        sudokuGroup.getSudokuFields().get(0).setFieldValue(1);
        assertTrue(sudokuGroup.verify());

        sudokuGroup.getSudokuFields().get(sudokuFiels.size() - 1).resetValue();
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
        sudokuGroup.getSudokuFields().get(4).resetValue();
        assertDoesNotThrow(() -> sudokuGroup.getSudokuFields().get(0).setFieldValue(5));

        assertThrows(IllegalFieldValueException.class, () -> sudokuGroup.getSudokuFields().get(1).setFieldValue(5));

        assertThrows(IllegalFieldValueException.class, () -> sudokuGroup.getSudokuFields().get(0).setFieldValue(10));
    }

    /**
     * Case description:
     * - setting equal value shoud not throw exception as of used listener property:
     * _    - In case the value set is same as that of the previous one, the event is not fired
     */
    @Test
    void isAllowedToSetEqualValueTestCase() {
        assertEquals(1, sudokuGroup.getSudokuFields().get(0).getFieldValue());

        sudokuGroup.getSudokuFields().get(0).setFieldValue(1);
        assertEquals(1, sudokuGroup.getSudokuFields().get(0).getFieldValue());
    }

    /**
     * Case description:
     * - toString should contains all variables
     */
    @Test
    void toStringTestCase() {
        String groupToString = sudokuGroup.toString();
        assertTrue(groupToString.contains("SudokuGroup"));
        for (SudokuField sudokuField : sudokuFiels) {
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
        assertEquals(sudokuGroup, new SudokuGroup(sudokuFiels) {});

        List<SudokuField> sudokuFields = Arrays.asList(sudokuFiels.stream().map(SudokuField::clone).toArray(SudokuField[]::new));
        for(int i=0; i<sudokuFields.size(); i++) {
            assertNotSame(sudokuFields.get(i), this.sudokuFiels.get(i));
        }
        assertEquals(sudokuGroup, new SudokuGroup(sudokuFiels) {});

        sudokuFields.set(0, new SudokuField(5));
        assertNotEquals(sudokuGroup, new SudokuGroup(sudokuFields) {});

        assertNotEquals(sudokuGroup, new SudokuGroup(Arrays.asList(new SudokuField[]{new SudokuField(1)})) {});
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

        assertEquals(sudokuGroup.hashCode(), new SudokuGroup(sudokuFiels) {}.hashCode());

        List<SudokuField> sudokuFields = Arrays.asList(sudokuFiels.stream().map(SudokuField::clone).toArray(SudokuField[]::new));
        for(int i=0; i<sudokuFields.size(); i++) {
            assertNotSame(sudokuFields.get(i), this.sudokuFiels.get(i));
        }
        assertEquals(sudokuGroup.hashCode(), new SudokuGroup(sudokuFiels) {}.hashCode());

        sudokuFields.set(0, new SudokuField(5));
        assertNotEquals(sudokuGroup.hashCode(), new SudokuGroup(sudokuFields) {}.hashCode());

        assertNotEquals(sudokuGroup.hashCode(), new SudokuGroup(Arrays.asList(new SudokuField[]{new SudokuField(1)})) {}.hashCode());
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
        for(int i=0; i<sudokuGroup.getSudokuFields().size(); i++) {
            assertNotSame(this.sudokuGroup.getSudokuFields().get(i), sudokuGroup.getSudokuFields().get(i));
        }

        int tmp = sudokuGroup.getSudokuFields().get(0).getFieldValue();
        sudokuGroup.getSudokuFields().get(0).resetValue();
        assertNotEquals(this.sudokuGroup, sudokuGroup);

        assertDoesNotThrow(() -> sudokuGroup.getSudokuFields().get(0).setFieldValue(tmp));
        assertThrows(IllegalFieldValueException.class, () -> sudokuGroup.getSudokuFields().get(0).setFieldValue(-1));
    }
}