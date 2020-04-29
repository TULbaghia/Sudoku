package pl.prokom.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SudokuBoardLevelTest {

    /**
     * Case description:
     * - difficulty level enum should be the same as it has been set.
     */
    @Test
    public void getEnumValuesTest() {
        SudokuBoardLevel sudokuBoardLevel = SudokuBoardLevel.EASY;
        Assertions.assertEquals(60, sudokuBoardLevel.getFilledCells());

        sudokuBoardLevel = SudokuBoardLevel.MEDIUM;
        Assertions.assertEquals(50, sudokuBoardLevel.getFilledCells());

        sudokuBoardLevel = SudokuBoardLevel.HARD;
        Assertions.assertEquals(40, sudokuBoardLevel.getFilledCells());
    }
}