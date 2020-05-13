package pl.prokom.model.partial.field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.model.exception.IllegalFieldValueException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {
    SudokuField sudokuField;

    @BeforeEach
    public void setUp() {
        sudokuField = new SudokuField();
    }

    /**
     * Case description:
     * - Empty constructor should create field with value equal to 0
     */
    @Test
    public void emptyConstructorTestCase() {
        sudokuField = new SudokuField();

        assertEquals(0, sudokuField.getFieldValue());
    }

    /**
     * Case description:
     * - Parametrized constructor sets value to given one
     */
    @Test
    public void parametrizedOneConstructorTestCase() {
        sudokuField = new SudokuField(1);

        assertEquals(1, sudokuField.getFieldValue());
    }

    /**
     * Case description:
     * - get value should be same as setting value
     */
    @Test
    public void setValueTestCase() {
        assertEquals(0, sudokuField.getFieldValue());

        sudokuField.setFieldValue(1);
        assertEquals(1, sudokuField.getFieldValue());

        sudokuField.setFieldValue(0);
        assertEquals(0, sudokuField.getFieldValue());

        sudokuField.setFieldValue(5);
        assertEquals(5, sudokuField.getFieldValue());

        sudokuField.setFieldValue(7);
        assertEquals(7, sudokuField.getFieldValue());
    }

    /**
     * Case description:
     * - values outside of range should throw exception
     */
    @Test
    public void exceptionSetValueTestCase() {
        assertThrows(IllegalFieldValueException.class, () -> sudokuField.setFieldValue(-1));
//        assertThrows(IllegalFieldValueException.class, () -> sudokuField.setFieldValue(0));
    }

    /**
     * Case description:
     * - resetting sets value to 0
     */
    @Test
    public void resetValueTestCase() {
        sudokuField.setFieldValue(5);
        assertEquals(5, sudokuField.getFieldValue());
        sudokuField.resetValue();
        assertEquals(0, sudokuField.getFieldValue());
    }

    /**
     * Case description:
     * - resetting sets value to 0
     */
    @Test
    public void comparingTest() {
        sudokuField.setFieldValue(5);
        SudokuField sudokuFieldOther = sudokuField.clone();
        assertEquals(0, sudokuField.compareTo(sudokuFieldOther));

        sudokuField.setFieldValue(1);
        assertEquals(-1, sudokuField.compareTo(sudokuFieldOther));

        sudokuField.setFieldValue(8);
        assertEquals(1, sudokuField.compareTo(sudokuFieldOther));

        assertThrows(NullPointerException.class, () -> sudokuField.compareTo(null));
    }

    /**
     * Case description:
     * - check if pcs is not null
     */
    @Test
    public void getPcsTest() {
        assertNotNull(sudokuField.getPcs());
    }

    /**
     * Case description:
     * - resetting sets value to 0
     */
    @Test
    public void addPropertyChangeListenerTestCase() {
        PropertyChangeListener pcl = propertyChangeEvent -> {
            assertEquals(0, propertyChangeEvent.getOldValue());
            assertEquals(1, propertyChangeEvent.getNewValue());
        };

        sudokuField.addPropertyChangeListener(pcl);
        sudokuField.setFieldValue(1);
    }

    /**
     * Case description:
     * - get value should be same as setting value
     */
    @Test
    public void toStringTestCase() {
        sudokuField = new SudokuField(5);
        String toString = sudokuField.toString();
        assertTrue(toString.contains("SudokuField"));
        assertTrue(toString.contains("value=" + sudokuField.getFieldValue()));
    }

    /**
     * Case description:
     * - object should be equal to self,
     * - object should be equal to created with same parameters
     */
    @Test
    public void equalsTestCase() {
        sudokuField = new SudokuField(3);
        assertEquals(sudokuField, sudokuField);

        assertNotEquals(sudokuField, null);
        assertNotEquals(sudokuField, "NE");

        assertEquals(sudokuField, new SudokuField(sudokuField.getFieldValue()));

        assertNotEquals(sudokuField, new SudokuField(sudokuField.getFieldValue() + 1));
    }

    /**
     * Case description:
     * - same object should return same hashcode
     * - object with same settings shoud return same hashCode
     * - object with different settings return different hashCode
     */
    @Test
    public void hashCodeTestCase() {
        sudokuField = new SudokuField(3);

        assertEquals(sudokuField.hashCode(), sudokuField.hashCode());
        assertEquals(sudokuField.hashCode(), (new SudokuField(sudokuField.getFieldValue())).hashCode());

        assertNotEquals(sudokuField.hashCode(), (new SudokuField(sudokuField.getFieldValue() + 1)).hashCode());
    }

    /**
     * Case description:
     * - cloned object should be equal to self
     * - cloned object is not same as original
     * - cloned object does not have listeners
     */
    @Test
    public void cloneTestCase() throws NoSuchFieldException, IllegalAccessException {
        Field field = sudokuField.getClass().getDeclaredField("pcs");
        field.setAccessible(true);

        sudokuField = new SudokuField(3);
        PropertyChangeListener pcl = propertyChangeEvent -> {};

        PropertyChangeSupport pcs_original = (PropertyChangeSupport) field.get(sudokuField);

        sudokuField.addPropertyChangeListener(pcl);
        assertEquals(1, pcs_original.getPropertyChangeListeners().length);

        SudokuField clone = sudokuField.clone();
        PropertyChangeSupport pcs_clone = (PropertyChangeSupport) field.get(clone);

        assertEquals(sudokuField, clone);
        assertNotSame(sudokuField, clone);

        assertEquals(1, pcs_original.getPropertyChangeListeners().length);
        assertEquals(0, pcs_clone.getPropertyChangeListeners().length);

    }
}