package pl.prokom.sudoku.partial.group;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import pl.prokom.sudoku.exception.IllegalFieldValueException;
import pl.prokom.sudoku.exception.IllegalPropertyChangeEventSourceException;
import pl.prokom.sudoku.partial.field.SudokuField;


//TODO: check whether sudokuFields's getter should return copy of array ??
//TODO: check whether derived methods should return copy of array and getSudokuFields just reference
//TODO: implement clone

/**
 * Class extended by every sudoku groups to help organize things and decrease code redundancy.
 */
public abstract class SudokuGroup implements PropertyChangeListener {
    /**
     * Stores references to given Fields.
     */
    private SudokuField[] sudokuFields;

    /**
     * Create object from given sudokuFields.
     *
     * @param sudokuFields group of fields- Box, Column, Row or other
     */
    public SudokuGroup(final SudokuField[] sudokuFields) {
        this.sudokuFields = sudokuFields;
        for (SudokuField sudokuField : this.sudokuFields) {
            sudokuField.addPropertyChangeListener(this);
        }
    }

    /**
     * Getter of {@code sudokuFields}.
     *
     * @return reference to {@code sudokuFields}
     */
    public SudokuField[] getSudokuFields() {
        return sudokuFields;
    }

    /**
     * Checks whether group is valid- every field should be filled with correct value.
     *
     * @return true when no errors
     */
    public final boolean verify() {
        SortedSet<Integer> uniqueValues = Arrays.stream(sudokuFields)
                .map(SudokuField::getFieldValue)
                .collect(Collectors.toCollection(TreeSet::new));

        return sudokuFields.length == uniqueValues.size()
                && uniqueValues.last() == sudokuFields.length
                && uniqueValues.first() == 1;
    }

    /**
     * Checks if given value already exists in board and if is within correct range.
     *
     * @return true when value exists; false otherwise
     */
    private boolean isValueAllowedToSet(final int value) {
        return Arrays.stream(sudokuFields)
                .map(SudokuField::getFieldValue)
                .noneMatch(x -> x == value)
                && value <= sudokuFields.length;
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
                + "sudokuFields=" + Arrays.deepToString(sudokuFields)
                + '}';
    }
}
