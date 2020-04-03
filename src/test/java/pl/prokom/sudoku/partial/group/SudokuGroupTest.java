package pl.prokom.sudoku.partial.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partial.field.SudokuField;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

class SudokuGroupTest {
    SudokuGroup sudokuGroup;
    SudokuField[] sudokuFields;

    @BeforeEach
    void setUp() {
        AtomicInteger index = new AtomicInteger(1);
        sudokuFields = new SudokuField[9];
        sudokuFields = Arrays.stream(sudokuFields)
                .map(x -> new SudokuField(index.getAndIncrement()))
                .toArray(SudokuField[]::new);

        sudokuGroup = new SudokuGroup(sudokuFields) {};
    }


    /**
     * Case description:
     * - checks if values are unique
     */
    @Test
    void verifyTestCase() {
        assertTrue(sudokuGroup.verify());

        sudokuGroup.getSudokuFields()[0].setFieldValue(2);
        assertFalse(sudokuGroup.verify());

        sudokuGroup.getSudokuFields()[0].resetValue();
        assertFalse(sudokuGroup.verify());

        sudokuGroup.getSudokuFields()[0].setFieldValue(1);
        assertTrue(sudokuGroup.verify());

        sudokuGroup.getSudokuFields()[sudokuFields.length-1].resetValue();
        assertFalse(sudokuGroup.verify());
    }

}