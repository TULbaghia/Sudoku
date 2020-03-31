package pl.prokom.sudoku.partials;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
     * - checks if values are unique
     */
    @Test
    void verifyTestCase() {
        assertTrue(sudokuPartial.verify());
        sudokuPartial.setFields(new SudokuField[]{new SudokuField(1), new SudokuField(1)});
        assertFalse(sudokuPartial.verify());
    }
}