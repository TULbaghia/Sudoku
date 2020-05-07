package pl.prokom.dao.file.exception;

import pl.prokom.dao.api.exception.DaoException;

public class DaoFileException extends DaoException {

    public DaoFileException() {
        super();
    }

    public DaoFileException(String message) {
        super(message);
    }

    public DaoFileException(Throwable cause) {
        super(cause);
    }

    public DaoFileException(String message, Throwable cause) {
        super(message, cause);
    }
}