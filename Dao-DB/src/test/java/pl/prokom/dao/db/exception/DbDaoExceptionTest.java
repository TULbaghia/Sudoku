package pl.prokom.dao.db.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DbDaoExceptionTest {

    @Test
    public void exceptionsTestCase() {
        assertThrows(DbDaoException.class, () -> {
            throw new DbDaoException();
        });
        assertThrows(DbDaoException.class, () -> {
            throw new DbDaoException("TEST");
        });
        assertThrows(DbDaoException.class, () -> {
            throw new DbDaoException(new NullPointerException());
        });
        assertThrows(DbDaoException.class, () -> {
            throw new DbDaoException("TEST", new NullPointerException());
        });
    }

    @Test
    public void getMessageTestCase() {
        var e = assertThrows(DbDaoException.class, () -> {
            throw new DbDaoException("TEST");
        });

        assertEquals("TEST", e.getMessage());
        assertEquals(e.getLocalizedMessage(), e.getMessage());
    }

    @Test
    public void getLocalizedMessageTestCase() {
        var e = assertThrows(DbDaoException.class, () -> {
            throw new DbDaoException("JdbcCallerException.illegalFile");
        });
        assertNotEquals("JdbcCallerException.illegalFile", e.getLocalizedMessage());

    }
}
