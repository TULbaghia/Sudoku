package pl.prokom.dao.file.api;

public interface Dao<T> {
    T read();

    void write(final T object);
}
