package pl.prokom.sudoku.partial.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partial.field.SudokuField;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {
    SudokuRow sudokuRow;
    SudokuField[] sudokuFields;

    @BeforeEach
    void setUp() {
        sudokuFields = new SudokuField[]{new SudokuField(1), new SudokuField(2)};
        sudokuRow = new SudokuRow(sudokuFields.clone());
    }

    @Test
    void getRowTestCase() {
        for (int i = 0; i < sudokuFields.length; i++) {
            assertEquals(sudokuFields[i], sudokuRow.getRow()[i]);
        }
    }
}