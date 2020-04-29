package pl.prokom.model.partial.group;

import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.prokom.model.partial.field.SudokuField;

/**
 * Class created to store each column in SudokuBoard.
 */
public class SudokuColumn extends SudokuGroup {
    public SudokuColumn(final List<SudokuField> sudokuFields) {
        super(sudokuFields);
    }

    public List<SudokuField> getColumn() {
        return super.getSudokuFields();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
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
