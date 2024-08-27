package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsernameValidationTest {
    UsernameValidation usernameValidationMock;
    @BeforeEach
    public void setUp(){
        usernameValidationMock=mock(UsernameValidation.class);
    }
    @ParameterizedTest
    @ValueSource(strings = {"","adi.8","adi_88","adi$$$"})
    void usernameIsEmptyOrNull(String username) {
        doThrow(new IllegalArgumentException("Mocked exception message")).when(usernameValidationMock).usernameIsEmptyOrNull(username);

        assertThrows(IllegalArgumentException.class,()->{
            usernameValidationMock.usernameIsEmptyOrNull(username);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"","adi.8","adi_88","adi$$$"})
    void verifyUsernameFormat(String username) {
        doThrow(new IllegalArgumentException("Mocked exception message")).when(usernameValidationMock).verifyUsernameFormat(username);

        assertThrows(IllegalArgumentException.class,()->{
            usernameValidationMock.verifyUsernameFormat(username);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"","adi.8","adi_88","adi$$$"})
    void isUsernameFormat(String username) {
        when(usernameValidationMock.isUsernameFormat(username)).thenReturn(false);

        boolean result=usernameValidationMock.isUsernameFormat(username);

        assertFalse(result);
    }
}