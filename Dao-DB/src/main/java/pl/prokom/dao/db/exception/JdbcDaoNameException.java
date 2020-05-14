package pl.prokom.dao.db.exception;

import pl.prokom.dao.api.exception.DaoException;

public class JdbcDaoNameException extends DaoException {
    public JdbcDaoNameException() {
        super();
    }

    public JdbcDaoNameException(String message) {
        super(message);
    }

    public JdbcDaoNameException(Throwable cause) {
        super(cause);
    }

    public JdbcDaoNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
