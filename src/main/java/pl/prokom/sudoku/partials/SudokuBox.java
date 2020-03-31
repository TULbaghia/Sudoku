package pl.prokom.sudoku.partials;

public class SudokuBox extends SudokuPartial {
    SudokuBox(SudokuField[] fields) {
        super(fields);
    }

    public SudokuField[] getBox() {
        return super.getFields();
    }
}
