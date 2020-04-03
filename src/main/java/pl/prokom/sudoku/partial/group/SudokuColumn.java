package pl.prokom.sudoku.partial.group;

import pl.prokom.sudoku.partial.field.SudokuField;

//TODO: implement clone, equals, hashcode

/**
 * Class created to store each column in SudokuBoard.
 */
public class SudokuColumn extends SudokuGroup {
    public SudokuColumn(final SudokuField[] sudokuFields) {
        super(sudokuFields);
    }

    public SudokuField[] getColumn() {
        return super.getSudokuFields();
    }

    @Override
    public String toString() {
        return "SudokuColumn{"
                + super.toString()
                + '}';
    }
}
