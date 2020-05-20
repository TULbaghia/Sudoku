package pl.prokom.dao.file.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileDaoExceptionTest {

    @Test
    public void exceptionsTestCase() {
        assertThrows(FileDaoException.class, () -> {
            throw new FileDaoException();
        });
        assertThrows(FileDaoException.class, () -> {
            throw new FileDaoException("TEST");
        });
        assertThrows(FileDaoException.class, () -> {
            throw new FileDaoException(new NullPointerException());
        });
        assertThrows(FileDaoException.class, () -> {
            throw new FileDaoException("TEST", new NullPointerException());
        });
    }

    @Test
    public void getMessageTestCase() {
        var e = assertThrows(FileDaoException.class, () -> {
            throw new FileDaoException("TEST");
        });

        assertEquals("TEST", e.getMessage());
        assertEquals(e.getLocalizedMessage(), e.getMessage());
    }

    @Test
    public void getLocalizedMessageTestCase() {
        var e = assertThrows(FileDaoException.class, () -> {
            throw new FileDaoException("DaoFileException.illegalFileAccess");
        });
        assertNotEquals("DaoFileException.illegalFileAccess", e.getLocalizedMessage());

    }
}
