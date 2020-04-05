package pl.prokom.sudoku.partial.group;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.prokom.sudoku.exception.IllegalFieldValueException;
import pl.prokom.sudoku.exception.IllegalPropertyChangeEventSourceException;
import pl.prokom.sudoku.partial.field.SudokuField;

//TODO: think if getSudokuFields should return deep clone of sudokuFields
//TODO: check whether derived methods should return deep copy of array and getSudokuFields reference

/**
 * Class extended by every sudoku groups to help organize things and decrease code redundancy.
 */
public abstract class SudokuGroup implements PropertyChangeListener, Cloneable {
    /**
     * Stores references to given Fields.
     */
    private List<SudokuField> sudokuFields;

    /**
     * Create object from given sudokuFields.
     *
     * @param sudokuFields group of fields- Box, Column, Row or other
     */
    public SudokuGroup(final List<SudokuField> sudokuFields) {
        setSudokuFields(sudokuFields);
    }

    /**
     * Getter of {@code sudokuFields}.
     *
     * @return reference to {@code sudokuFields}
     */
    public List<SudokuField> getSudokuFields() {
        return sudokuFields;
    }

    /**
     * Setter of {@code sudokuFields}.
     *
     * @param sudokuFields group of fields- Box, Column, Row or other
     */
    private void setSudokuFields(final List<SudokuField> sudokuFields) {
        this.sudokuFields = sudokuFields;
        this.sudokuFields.forEach(x -> x.addPropertyChangeListener(this));
    }

    /**
     * Checks whether group is valid- every field should be filled with correct value.
     *
     * @return true when no errors
     */
    public final boolean verify() {
        SortedSet<Integer> uniqueValues = sudokuFields.stream()
                .map(SudokuField::getFieldValue)
                .collect(Collectors.toCollection(TreeSet::new));

        return sudokuFields.size() == uniqueValues.size()
                && uniqueValues.last() == sudokuFields.size()
                && uniqueValues.first() == 1;
    }

    /**
     * Checks if given value already exists in board and if is within correct range.
     *
     * @return true when value exists; false otherwise
     */
    private boolean isValueAllowedToSet(final int value) {
        return sudokuFields.stream()
                .map(SudokuField::getFieldValue)
                .noneMatch(x -> x == value)
                && value <= sudokuFields.size();
    }

    /**
     * Listener that triggers when property is going to change in field.
     * Validates if change is allowed in this group
     *
     * @param pce description of occuring changes
     */
    @Override
    public void propertyChange(final PropertyChangeEvent pce) {
        if (pce.getSource() instanceof SudokuField) {
            if (!isValueAllowedToSet((int) pce.getNewValue())) {
                throw new IllegalFieldValueException("Given value already exists in group.");
            }
        } else {
            throw new IllegalPropertyChangeEventSourceException(
                    pce.getSource().getClass().toString() + "is illegall caller.");
        }
    }

    @Override
    public String toString() {
        return "SudokuGroup{"
                + "sudokuFields=" + sudokuFields.toString()
                + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SudokuGroup)) {
            return false;
        }
        SudokuGroup that = (SudokuGroup) object;

        return new EqualsBuilder()
                .append(this.sudokuFields, that.sudokuFields)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sudokuFields)
                .toHashCode();
    }

    @Override
    public SudokuGroup clone() {
        try {
            SudokuGroup sudokuGroup = (SudokuGroup) super.clone();
            sudokuGroup.setSudokuFields(Arrays.asList(sudokuFields.stream()
                    .map(SudokuField::clone)
                    .toArray(SudokuField[]::new)));

            return sudokuGroup;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
