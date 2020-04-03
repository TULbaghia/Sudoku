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

    public SudokuField[][] getBox2D() {
        SudokuField[] fields = getSudokuFields();
        int size = (int) Math.sqrt(fields.length);
        SudokuField[][] sudokuFields = new SudokuField[size][size];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                sudokuFields[row][column] = fields[row * size + column];
            }
        }

        return sudokuFields;
    }

}
