package pl.prokom.view.exception;

public class SudokuBoardDuplicateValuesException extends UnsupportedOperationException {
    public SudokuBoardDuplicateValuesException(final String message) {
        super(message);
    }
}
