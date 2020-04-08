package pl.prokom.sudoku.partial.group;

import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.prokom.sudoku.partial.field.SudokuField;

//TODO: change getBox2D from array to List<List<>>

/**
 * Class created to store each box in SudokuBoard.
 */
public class SudokuBox extends SudokuGroup {
    public SudokuBox(final List<SudokuField> sudokuFields) {
        super(sudokuFields);
    }

    public List<SudokuField> getBox() {
        return super.getSudokuFields();
    }

    public SudokuField[][] getBox2D() {
        SudokuField[] fields = getSudokuFields().toArray(SudokuField[]::new);
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        return new EqualsBuilder()
                .appendSuper(super.equals(object))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(this.getClass().getName())
                .toHashCode();
    }

    @Override
    public SudokuBox clone() {
        return (SudokuBox) super.clone();
    }
}