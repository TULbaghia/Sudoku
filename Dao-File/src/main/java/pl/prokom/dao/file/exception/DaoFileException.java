package pl.prokom.dao.file.exception;

public class DaoFileException extends FileDaoException {
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