package pl.prokom.sudoku.exception;

public class IllegalFieldValueException extends IllegalArgumentException {
    public IllegalFieldValueException(final String string) {
        super(string);
    }
}
