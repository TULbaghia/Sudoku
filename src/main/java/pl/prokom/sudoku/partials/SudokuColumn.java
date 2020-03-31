package pl.prokom.sudoku.partials;

public class SudokuColumn extends SudokuPartial {
    public SudokuColumn(SudokuField[] fields) {
        super(fields);
    }

    public SudokuField[] getColumn() {
        return super.getFields();
    }
}
