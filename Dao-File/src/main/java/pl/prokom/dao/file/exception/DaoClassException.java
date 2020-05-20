package pl.prokom.dao.file.exception;

public class DaoClassException extends FileDaoException {
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
