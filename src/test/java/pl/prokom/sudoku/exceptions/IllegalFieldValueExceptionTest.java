package pl.prokom.sudoku.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IllegalFieldValueExceptionTest {

    @Test
    void ExceptionTestCase() {
        assertThrows(IllegalFieldValueException.class, () -> {
            throw new IllegalFieldValueException("TEST");
        });
    }
}