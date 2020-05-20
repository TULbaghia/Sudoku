package pl.prokom.dao.db.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JdbcCallerExceptionTest {

    @Test
    public void exceptionsTest() {
        assertThrows(JdbcCallerException.class, () -> {
            throw new JdbcCallerException();
        });
        assertThrows(JdbcCallerException.class, () -> {
            throw new JdbcCallerException("TEST");
        });
        assertThrows(JdbcCallerException.class, () -> {
            throw new JdbcCallerException(new NullPointerException());
        });
        assertThrows(JdbcCallerException.class, () -> {
            throw new JdbcCallerException("TEST", new NullPointerException());
        });
    }
}
