package pl.prokom.dao.db.exception;

import java.util.ResourceBundle;
import pl.prokom.dao.api.exception.DaoException;

public class DbDaoException extends DaoException {
    public DbDaoException() {
        super();
    }

    public DbDaoException(String message) {
        super(message);
    }

    public DbDaoException(Throwable cause) {
        super(cause);
    }

    public DbDaoException(String message, Throwable cause) {
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
