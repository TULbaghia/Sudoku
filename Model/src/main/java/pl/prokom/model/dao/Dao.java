package pl.prokom.model.dao;

import java.io.IOException;

public interface Dao<T> {

    T read() throws IOException, ClassNotFoundException;

    void write(final T object) throws IOException;

}
