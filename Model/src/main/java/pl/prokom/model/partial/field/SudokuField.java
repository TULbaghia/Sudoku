package pl.prokom.model.partial.field;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.prokom.model.exception.IllegalFieldValueException;

/**
 * Made for storing fields values in SudokuBoard and other collections.
 */
public class SudokuField implements Cloneable, Serializable, Comparable<SudokuField> {
    /**
     * Handles observers of created object.
     */
    private PropertyChangeSupport pcs;

    /**
     * Stores value of field.
     */
    private int value;

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
        pcs = new PropertyChangeSupport(this);
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
        if (value < 1) {
            throw new IllegalFieldValueException("Value '" + value + "' is not in allowed range.");
        }
        if (this.value != value) {
            pcs.firePropertyChange("value", this.value, value);
            this.value = value;
        }
    }

    /**
     * Method to reset field {@code this.value} to 0.
     */
    public void resetValue() {
        this.value = 0;
    }

    /**
     * Allows to assign Observer to this Observable.
     *
     * @param listener object that thould be notified when value change occurs
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        SudokuField that = (SudokuField) object;

        return new EqualsBuilder()
                .append(this.value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getClass().getName())
                .append(this.value)
                .toHashCode();
    }

    @Override
    public SudokuField clone() {
        try {
            SudokuField sudokuField = (SudokuField) super.clone();
            sudokuField.pcs = new PropertyChangeSupport(sudokuField);
            return sudokuField;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int compareTo(SudokuField sudokuField) throws NullPointerException {
        if(sudokuField == null) throw new NullPointerException();
        return Integer.compare(this.getFieldValue(), sudokuField.getFieldValue());
    }
}
