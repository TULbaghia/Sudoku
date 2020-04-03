package pl.prokom.sudoku.partial.group;

import pl.prokom.sudoku.partial.field.SudokuField;

//TODO: implement clone, equals, hashcode

/**
 * Class created to store each row in SudokuBoard.
 */
public class SudokuRow extends SudokuGroup {
    public SudokuRow(final SudokuField[] sudokuFields) {
        super(sudokuFields);
    }

    public SudokuField[] getRow() {
        return super.getSudokuFields();
    }

    @Override
    public String toString() {
        return "SudokuRow{"
                + super.toString()
                + '}';
    }
}
