package pl.prokom.dao.db.exception;

public class JdbcDaoConnectionException extends DbDaoException {
    public JdbcDaoConnectionException() {
        super();
    }

    public JdbcDaoConnectionException(String message) {
        super(message);
    }

    public JdbcDaoConnectionException(Throwable cause) {
        super(cause);
    }

    public JdbcDaoConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
