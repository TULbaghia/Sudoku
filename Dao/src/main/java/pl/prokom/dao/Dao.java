package pl.prokom.dao;

public interface Dao<T> {

    T read();

    void write(final T object);

}
