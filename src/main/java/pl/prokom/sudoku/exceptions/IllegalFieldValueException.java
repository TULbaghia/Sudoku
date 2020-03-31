package pl.prokom.sudoku.exceptions;

public class IllegalFieldValueException extends IllegalArgumentException {
    public IllegalFieldValueException(final String s) {
        super(s);
    }
}
