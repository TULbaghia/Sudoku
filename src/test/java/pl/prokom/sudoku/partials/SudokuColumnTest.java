package pl.prokom.sudoku.partials;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {
    SudokuColumn sudokuColumn;
    SudokuField[] sudokuFields;

    @BeforeEach
    void setUp() {
        sudokuFields = new SudokuField[]{new SudokuField(1), new SudokuField(2)};
        sudokuColumn = new SudokuColumn(sudokuFields.clone());
    }

    @Test
    void getColumnTestCase() {
        for (int i = 0; i < sudokuFields.length; i++) {
            assertEquals(sudokuFields[i], sudokuColumn.getColumn()[i]);
        }
    }
}