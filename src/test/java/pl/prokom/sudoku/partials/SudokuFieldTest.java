package pl.prokom.sudoku.partials;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.SudokuBoard;
import pl.prokom.sudoku.exceptions.IllegalFieldValueException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {
    SudokuField sudokuField;

    @BeforeEach
    void setUp() {
        sudokuField = new SudokuField();
    }

    @Test
    void getFieldValue() {
        assertEquals(0, sudokuField.getFieldValue());
    }

    @Test
    void setFieldValuePositiveTaseCase() {
        sudokuField.setFieldValue(1);
        assertEquals(1, sudokuField.getFieldValue());
        sudokuField.setFieldValue(2);
        assertEquals(2, sudokuField.getFieldValue());
        sudokuField.setFieldValue(3);
        assertEquals(3, sudokuField.getFieldValue());
    }

    @Test
    void setFieldValueNegativeTaseCase() {
        assertThrows(IllegalFieldValueException.class, () -> sudokuField.setFieldValue(0));
        assertThrows(IllegalFieldValueException.class, () -> sudokuField.setFieldValue(-1));
        assertThrows(IllegalFieldValueException.class, () -> sudokuField.setFieldValue(
                SudokuBoard.getMiniSquareCount() * SudokuBoard.getMiniSquareCount()+1));
    }

    @Test
    void testEqualsSameTestCase() {
        assertEquals(sudokuField, sudokuField);
    }

    @Test
    void testEqualsNullTestCase() {
        assertNotEquals(sudokuField, null);
    }

    @Test
    void testEqualsClassTestCase() {
        assertNotEquals(sudokuField, new SudokuBoard());
    }

    @Test
    void testEqualsCloneTestCase() throws CloneNotSupportedException {
        assertEquals(sudokuField, sudokuField.clone());
    }

}