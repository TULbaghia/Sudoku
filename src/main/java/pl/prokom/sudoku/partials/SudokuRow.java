package pl.prokom.sudoku.partials;

public class SudokuRow extends SudokuPartial {
    public SudokuRow(SudokuField[] fields) {
        super(fields);
    }

    public SudokuField[] getRow() {
        return super.getFields();
    }
}
