package pl.prokom.dao.file.exception;

import java.util.ResourceBundle;
import pl.prokom.dao.api.exception.DaoException;

public class FileDaoException extends DaoException {
    public FileDaoException() {
        super();
    }

    public FileDaoException(String message) {
        super(message);
    }

    public FileDaoException(Throwable cause) {
        super(cause);
    }

    public FileDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return getLocalizedMessage();
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("exception");
        if (resourceBundle.containsKey(super.getMessage())) {
            return resourceBundle.getString(super.getMessage());
        }
        return super.getMessage();
    }
}
