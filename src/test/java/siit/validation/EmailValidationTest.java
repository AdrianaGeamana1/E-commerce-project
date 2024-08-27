package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailValidationTest {
    EmailValidation emailValidationMock;
    @BeforeEach
    public void setUp(){
        emailValidationMock=mock(EmailValidation.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"","adr.io","adri.b@gmail.com"})
    void emailIsEmptyOrNull(String email) {
        doThrow(new IllegalArgumentException("Mocked exception message")).when(emailValidationMock).emailIsEmptyOrNull(email);

        assertThrows(IllegalArgumentException.class,()->{
            emailValidationMock.emailIsEmptyOrNull(email);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"","adr.io","adri.b@gmail.com"})
    void verifyEmailFormat(String email) {
        doThrow(new IllegalArgumentException("Mocked exception message")).when(emailValidationMock).verifyEmailFormat(email);

        assertThrows(IllegalArgumentException.class,()->{
            emailValidationMock.verifyEmailFormat(email);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"","adr.io","adri.b@gmail.com"})
    void isEmailFormat(String email) {
        when(emailValidationMock.isEmailFormat(email)).thenReturn(true);

        boolean result=emailValidationMock.isEmailFormat(email);

        assertTrue(result);
    }
}