package pl.prokom.sudoku.partial.group;

import pl.prokom.sudoku.partial.field.SudokuField;

/**
 * Class created to store each column in SudokuBoard.
 */
public class SudokuColumn extends SudokuGroup {
    public SudokuColumn(SudokuField[] sudokuFields) {
        super(sudokuFields);
    }

    public SudokuField[] getColumn() {
        return super.getSudokuFields();
    }
}
