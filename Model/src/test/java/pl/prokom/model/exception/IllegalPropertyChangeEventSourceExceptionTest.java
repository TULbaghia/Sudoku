package pl.prokom.model.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IllegalPropertyChangeEventSourceExceptionTest {

    /**
     * Case description:
     * - correct throw of exception
     * - thrown message equal to given
     */
    @Test
    public void ConstructorTestCase() {
        String exceptionMessage = "Custom test message";
        IllegalPropertyChangeEventSourceException exception = assertThrows(IllegalPropertyChangeEventSourceException.class, () -> {
            throw new IllegalPropertyChangeEventSourceException(exceptionMessage);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

}