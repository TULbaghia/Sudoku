package pl.prokom.dao.db.exception;

import pl.prokom.dao.api.exception.DaoException;

public class JdbcDaoConnectionException extends DaoException {
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
