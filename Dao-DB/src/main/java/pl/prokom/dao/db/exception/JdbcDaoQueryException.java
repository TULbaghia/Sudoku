package pl.prokom.dao.db.exception;

import pl.prokom.dao.api.exception.DaoException;

public class JdbcDaoQueryException extends DaoException {
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
