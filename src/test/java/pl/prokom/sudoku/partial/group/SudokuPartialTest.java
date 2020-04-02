package pl.prokom.sudoku.partial.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partial.field.SudokuField;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SudokuPartialTest {
    SudokuPartial sudokuPartial;
    SudokuField[] sudokuFields;

    @BeforeEach
    void setUp() {
        List<SudokuField> sudokuFields = new ArrayList<>();

        for(int i=1; i<10; i++) {
            sudokuFields.add(new SudokuField(i));
        }

        Collections.shuffle(sudokuFields);
        this.sudokuFields = sudokuFields.toArray(new SudokuField[0]).clone();

        sudokuPartial = new SudokuPartial(sudokuFields.toArray(new SudokuField[0]).clone()) {};
    }

    /**
     * Case description:
     * - checks if getFields return all items in given order
     */
    @Test
    void getFieldsTestCase() {
        assertEquals(sudokuFields.length, sudokuPartial.getFields().length);
        for(int i=0; i<sudokuFields.length; i++) {
            assertEquals(sudokuFields[i].getFieldValue(), sudokuPartial.getFields()[i].getFieldValue());
        }
    }

    /**
     * Case description:
     * - checks if values are unique and in correct order
     */
    @Test
    void verifyTestCase() {
        assertTrue(sudokuPartial.verify());
        sudokuPartial.setFields(new SudokuField[]{new SudokuField(1), new SudokuField(1)});
        assertFalse(sudokuPartial.verify());

        sudokuPartial.setFields(new SudokuField[]{new SudokuField(), new SudokuField(1)});
        assertFalse(sudokuPartial.verify());

        sudokuPartial.setFields(new SudokuField[]{new SudokuField(1), new SudokuField(4)});
        assertFalse(sudokuPartial.verify());
    }
}