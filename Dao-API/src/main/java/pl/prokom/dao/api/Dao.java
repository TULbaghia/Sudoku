package pl.prokom.dao.api;

public interface Dao<T> {
    T read();

    void write(final T object);
}
