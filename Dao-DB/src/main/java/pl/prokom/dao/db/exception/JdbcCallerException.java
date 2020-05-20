package pl.prokom.dao.db.exception;

public class JdbcCallerException extends DbDaoException {
    public JdbcCallerException() {
        super();
    }

    public JdbcCallerException(String message) {
        super(message);
    }

    public JdbcCallerException(Throwable cause) {
        super(cause);
    }

    public JdbcCallerException(String message, Throwable cause) {
        super(message, cause);
    }
}
