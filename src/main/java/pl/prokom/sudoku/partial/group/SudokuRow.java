package pl.prokom.sudoku.partial.group;

import pl.prokom.sudoku.partial.field.SudokuField;

/**
 * Class created to store each row in SudokuBoard.
 */
public class SudokuRow extends SudokuGroup {
    public SudokuRow(SudokuField[] sudokuFields) {
        super(sudokuFields);
    }

    public SudokuField[] getRow() {
        return super.getSudokuFields();
    }
}
