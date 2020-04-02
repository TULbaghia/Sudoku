package pl.prokom.sudoku.partial.group;

import pl.prokom.sudoku.partial.field.SudokuField;

public class SudokuColumn extends SudokuPartial {
    public SudokuColumn(SudokuField[] fields) {
        super(fields);
    }

    public SudokuField[] getColumn() {
        return super.getFields();
    }
}
