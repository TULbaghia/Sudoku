package pl.prokom.sudoku.partial.group;

import pl.prokom.sudoku.partial.field.SudokuField;

public class SudokuBox extends SudokuPartial {
    public SudokuBox(SudokuField[] fields) {
        super(fields);
    }

    public SudokuField[] getBox() {
        return super.getFields();
    }
}
