package pl.prokom.sudoku.partial.group;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import pl.prokom.sudoku.partial.field.SudokuField;

/**
 * Class extended by every sudoku groups to help organize things and decrease code redundancy.
 */
public abstract class SudokuGroup {

    /**
     * Stores references to given Fields.
     */
    private SudokuField[] sudokuFields;

    /**
     * Create object from given sudokuFields.
     *
     * @param sudokuFields group of fields- Box, Column, Row or other
     */
    public SudokuGroup(SudokuField[] sudokuFields) {
        this.sudokuFields = sudokuFields;
    }

    public void setFields(SudokuField[] fields) {
        this.sudokuFields = fields.clone();
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
     * Checks whether values in given method don't break general Sudoku rule.
     *
     * @return true when no errors
     */
    public boolean verify() {
        SortedSet<Integer> uniqueValues = Arrays.stream(sudokuFields)
                .map(SudokuField::getFieldValue)
                .collect(Collectors.toCollection(TreeSet::new));

        return sudokuFields.length == uniqueValues.size()
                && uniqueValues.last() == sudokuFields.length
                && uniqueValues.first() == 1;
    }

}
