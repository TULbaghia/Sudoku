package pl.prokom.model.dao;

import java.io.IOException;

public interface Dao<T> {

    T read();

    void write(final T object);

}
