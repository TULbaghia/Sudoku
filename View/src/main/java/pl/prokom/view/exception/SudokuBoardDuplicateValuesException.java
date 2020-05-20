package pl.prokom.view.exception;

import pl.prokom.model.exception.IllegalFieldValueException;
import pl.prokom.view.bundles.BundleHelper;

public class SudokuBoardDuplicateValuesException extends IllegalFieldValueException {
    public SudokuBoardDuplicateValuesException(final String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return BundleHelper.getException(super.getMessage());
    }
}
