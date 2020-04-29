package pl.prokom.dao.api;

public interface Dao<T> extends AutoCloseable {
    T read();

    void write(final T object);
}
