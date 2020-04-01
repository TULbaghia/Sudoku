package pl.prokom.sudoku.partials;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoxTest {
    SudokuBox sudokuBox;
    SudokuField[] sudokuFields;

    @BeforeEach
    void setUp() {
        sudokuFields = new SudokuField[]{new SudokuField(1), new SudokuField(2)};
        sudokuBox = new SudokuBox(sudokuFields.clone());
    }

    @Test
    void getBoxTestCase() {
        for (int i = 0; i < sudokuFields.length; i++) {
            assertEquals(sudokuFields[i], sudokuBox.getBox()[i]);
        }
    }
}