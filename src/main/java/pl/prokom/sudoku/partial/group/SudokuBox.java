package pl.prokom.sudoku.partial.group;

import pl.prokom.sudoku.partial.field.SudokuField;

//TODO: implement clone, equals, hashcode

/**
 * Class created to store each box in SudokuBoard.
 */
public class SudokuBox extends SudokuGroup {
    public SudokuBox(final SudokuField[] sudokuFields) {
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
            System.arraycopy(fields, row * size, sudokuFields[row], 0, size);
        }

        return sudokuFields;
    }

    @Override
    public String toString() {
        return "SudokuBox{"
                + super.toString()
                + '}';
    }
}
