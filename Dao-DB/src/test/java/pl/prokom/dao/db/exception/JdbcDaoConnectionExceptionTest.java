package pl.prokom.dao.db.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JdbcDaoConnectionExceptionTest {

    @Test
    public void exceptionsTest() {
        assertThrows(JdbcDaoConnectionException.class, () -> {
            throw new JdbcDaoConnectionException();
        });
        assertThrows(JdbcDaoConnectionException.class, () -> {
            throw new JdbcDaoConnectionException("TEST");
        });
        assertThrows(JdbcDaoConnectionException.class, () -> {
            throw new JdbcDaoConnectionException(new NullPointerException());
        });
        assertThrows(JdbcDaoConnectionException.class, () -> {
            throw new JdbcDaoConnectionException("TEST", new NullPointerException());
        });
    }

}
