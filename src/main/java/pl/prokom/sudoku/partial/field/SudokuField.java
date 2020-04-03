package pl.prokom.sudoku.partial.field;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.prokom.sudoku.SudokuBoard;
import pl.prokom.sudoku.exceptions.IllegalFieldValueException;

/**
 * Made for storing fields values in SudokuBoard and other collections.
 */

public class SudokuField implements Cloneable {
    /**
     * Stores value of field.
     */
    private int value;

    /**
     * Holds maximum value that can be assigned to {@code value}.
     */
    private final int maxValue;

    /**
     * Constructor calls {@code SudokuField(0)} with default {@code value = 0}.
     */
    public SudokuField() {
        this(0);
    }

    /**
     * Constructor calls {@code SudokuField(value, 9)} with default {@code maxValue = 9}.
     *
     * @param value value that has to be assigned to {@code this.value}
     */
    public SudokuField(final int value) {
        this(value, 9);
    }

    /**
     * Constructor assigns maxValue and value.
     * Throws exception when value not in given range [0..maxValue]
     *
     * @param value    value that has to be assigned to {@code this.value}
     * @param maxValue value that has to be assigned to {@code this.maxValue}
     */
    public SudokuField(final int value, final int maxValue) {
        this.maxValue = maxValue;
        if (value != 0) {
            setFieldValue(value);
        }
    }

    /**
     * Getter for {@code value}.
     *
     * @return value of this.value
     */
    public final int getFieldValue() {
        return this.value;
    }

    /**
     * Setter for {@code this.value}.
     *
     * @param value value that has to be assigned to {@code this.value}
     * @throws IllegalFieldValueException when value not in given range
     */
    public void setFieldValue(final int value) throws IllegalFieldValueException {
        if (1 > value || maxValue < value) {
            throw new IllegalFieldValueException("Value '" + value + "' is not in allowed range.");
        }
        this.value = value;
    }

    /**
     * Method to reset field {@code this.value} to 0.
     */
    public void resetValue() {
        this.value = 0;
    }

    @Override
    public String toString() {
        return "SudokuField{"
                + "value=" + this.value
                + ", maxValue=" + this.maxValue
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

        SudokuField that = (SudokuField) object;

        return new EqualsBuilder()
                .append(this.value, that.value)
                .append(this.maxValue, that.maxValue)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.value)
                .append(this.maxValue)
                .toHashCode();
    }


    @Override
    public final SudokuField clone() {
        try {
            return (SudokuField) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
