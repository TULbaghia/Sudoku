package pl.prokom.dao.file.exception;

import pl.prokom.dao.api.exception.DaoException;

public class DaoClassException extends DaoException {
    public DaoClassException() {
        super();
    }

    public DaoClassException(String message) {
        super(message);
    }

    public DaoClassException(Throwable cause) {
        super(cause);
    }

    public DaoClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
