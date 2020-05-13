package pl.prokom.view.exception;

import pl.prokom.model.exception.IllegalFieldValueException;

public class SudokuBoardDuplicateValuesException extends IllegalFieldValueException {
    public SudokuBoardDuplicateValuesException(final String message) {
        super(message);
    }
}
