package pl.prokom.dao.api.model;

import pl.prokom.dao.api.exception.DaoException;

public interface Dao<T> extends AutoCloseable {
    T read() throws DaoException;

    void write(final T object) throws DaoException;
}
