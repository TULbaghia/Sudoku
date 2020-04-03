package pl.prokom.sudoku.partial.group;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
    public SudokuColumn clone() {
        return (SudokuColumn) super.clone();
    }

}
