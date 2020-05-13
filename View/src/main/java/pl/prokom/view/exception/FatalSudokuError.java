package pl.prokom.view.exception;

public class FatalSudokuError extends Error {
    public FatalSudokuError() {
    }

    public FatalSudokuError(String message) {
        super(message);
    }

    public FatalSudokuError(String message, Throwable cause) {
        super(message, cause);
    }

    public FatalSudokuError(Throwable cause) {
        super(cause);
    }
}
