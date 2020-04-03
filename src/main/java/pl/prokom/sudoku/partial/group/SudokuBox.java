package pl.prokom.sudoku.partial.group;

import pl.prokom.sudoku.partial.field.SudokuField;

/**
 * Class created to store each box in SudokuBoard.
 */
public class SudokuBox extends SudokuGroup {
    public SudokuBox(SudokuField[] sudokuFields) {
        super(sudokuFields);
    }

    public SudokuField[] getBox() {
        return super.getSudokuFields();
    }
}
