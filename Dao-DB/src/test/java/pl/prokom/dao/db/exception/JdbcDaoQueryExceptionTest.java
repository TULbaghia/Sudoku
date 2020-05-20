package pl.prokom.dao.db.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JdbcDaoQueryExceptionTest {

    @Test
    public void exceptionsTest() {
        assertThrows(JdbcDaoQueryException.class, () -> {
            throw new JdbcDaoQueryException();
        });
        assertThrows(JdbcDaoQueryException.class, () -> {
            throw new JdbcDaoQueryException("TEST");
        });
        assertThrows(JdbcDaoQueryException.class, () -> {
            throw new JdbcDaoQueryException(new NullPointerException());
        });
        assertThrows(JdbcDaoQueryException.class, () -> {
            throw new JdbcDaoQueryException("TEST", new NullPointerException());
        });
    }
}
