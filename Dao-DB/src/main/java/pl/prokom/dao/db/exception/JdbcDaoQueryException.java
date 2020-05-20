package pl.prokom.dao.db.exception;

public class JdbcDaoQueryException extends DbDaoException {
    public JdbcDaoQueryException() {
        super();
    }

    public JdbcDaoQueryException(String message) {
        super(message);
    }

    public JdbcDaoQueryException(Throwable cause) {
        super(cause);
    }

    public JdbcDaoQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
