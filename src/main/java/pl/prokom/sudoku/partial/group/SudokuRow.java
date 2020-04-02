package pl.prokom.sudoku.partial.group;

import pl.prokom.sudoku.partial.field.SudokuField;

public class SudokuRow extends SudokuGroup {
    public SudokuRow(SudokuField[] fields) {
        super(fields);
    }

    public SudokuField[] getRow() {
        return super.getFields();
    }
}
