package pl.prokom.sudoku.partials;

import org.apache.commons.lang3.builder.EqualsBuilder;
import pl.prokom.sudoku.SudokuBoard;
import pl.prokom.sudoku.exceptions.IllegalFieldValueException;

public class SudokuField implements Cloneable {
    int value;

    public int getFieldValue() {
        return value;
    }

    public SudokuField() {
        this.value = 0;
    }

    public SudokuField(int value) {
        setFieldValue(value);
    }

    public void setFieldValue(int value) throws IllegalFieldValueException {
        if (!(value > 0 && value <= SudokuBoard.getSquareSize())) {
            throw new IllegalFieldValueException("Wartosc jest nieprawidlowa. Podano: " + value);
        }
        this.value = value;
    }

    public void reset()  {
        this.value = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SudokuField that = (SudokuField) obj;

        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
