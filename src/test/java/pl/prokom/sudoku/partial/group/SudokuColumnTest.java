package pl.prokom.sudoku.partial.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.partial.field.SudokuField;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {
    SudokuField[] sudokuFields;
    SudokuColumn sudokuColumn;

    @BeforeEach
    void setUp() {
        AtomicInteger index = new AtomicInteger(1);
        sudokuFields = new SudokuField[9];
        sudokuFields = Arrays.stream(sudokuFields)
                .map(x -> new SudokuField(index.getAndIncrement()))
                .toArray(SudokuField[]::new);

        sudokuColumn = new SudokuColumn(sudokuFields);
    }

    /**
     * Case description:
     * - getColumn return the same object that was pushed in SudokuColumn
     */
    @Test
    void getColumnTestCase() {
        assertArrayEquals(sudokuFields, sudokuColumn.getColumn());
        assertSame(sudokuFields, sudokuColumn.getColumn());
        sudokuFields[0].resetValue();

        for (int i = 0; i < sudokuFields.length; i++) {
            assertSame(sudokuFields[i], sudokuColumn.getColumn()[i]);
        }

        sudokuFields[1].resetValue();
        assertTrue(Arrays.deepEquals(sudokuFields, sudokuColumn.getColumn()));
    }

    /**
     * Case description:
     * - toString should contains all variables
     */
    @Test
    void toStringTestCase() {
        String groupToString = sudokuColumn.toString();
        assertTrue(groupToString.contains("SudokuColumn"));
        assertTrue(groupToString.contains((new SudokuGroup(sudokuFields){}).toString()));
    }
}