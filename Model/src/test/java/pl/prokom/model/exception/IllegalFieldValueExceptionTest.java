package pl.prokom.model.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IllegalFieldValueExceptionTest {

    /**
     * Case description:
     * - correct throw of exception
     * - thrown message equal to given
     */
    @Test
    public void ConstructorTestCase() {
        String exceptionMessage = "Custom test message";
        IllegalFieldValueException exception = assertThrows(IllegalFieldValueException.class, () -> {
            throw new IllegalFieldValueException(exceptionMessage);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }
}