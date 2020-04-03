package pl.prokom.sudoku.partial.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partial.field.SudokuField;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {
    SudokuRow sudokuRow;
    SudokuField[] sudokuFields;

    @BeforeEach
    void setUp() {
        AtomicInteger index = new AtomicInteger(1);
        sudokuFields = new SudokuField[9];
        sudokuFields = Arrays.stream(sudokuFields)
                .map(x -> new SudokuField(index.getAndIncrement()))
                .toArray(SudokuField[]::new);

        sudokuRow = new SudokuRow(sudokuFields);
    }


    /**
     * Case description:
     * - getRow return the same object that was pushed in SudokuRow
     */
    @Test
    void getRowTestCase() {
        assertArrayEquals(sudokuFields, sudokuRow.getRow());
        assertSame(sudokuFields, sudokuRow.getRow());
        sudokuFields[0].resetValue();

        for (int i = 0; i < sudokuFields.length; i++) {
            assertSame(sudokuFields[i], sudokuRow.getRow()[i]);
        }

        sudokuFields[1].resetValue();
        assertTrue(Arrays.deepEquals(sudokuFields, sudokuRow.getRow()));
    }

}