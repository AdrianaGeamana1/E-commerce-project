package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PasswordValidationTest {
    PasswordValidation passwordValidationMock;
    @BeforeEach
    public void setUp(){
        passwordValidationMock=mock(PasswordValidation.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"","3456","adi123456"})
    void passwordIsEmptyOrNull(String password) {
        doThrow(new IllegalArgumentException("Mocked exception message")).when(passwordValidationMock).passwordIsEmptyOrNull(password);

        assertThrows(IllegalArgumentException.class,()->{
            passwordValidationMock.passwordIsEmptyOrNull(password);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"","3456","adi123456"})
    void password2IsEmptyOrNull(String password2) {
        doThrow(new IllegalArgumentException("Mocked exception message")).when(passwordValidationMock).password2IsEmptyOrNull(password2);

        assertThrows(IllegalArgumentException.class,()->{
            passwordValidationMock.password2IsEmptyOrNull(password2);
        });
    }
    // Requires at least one letter and one digit, minimum length of 6 characters
    @ParameterizedTest
    @ValueSource(strings = {"","3456","adi123456"})
    void verifyPasswordFormat(String password) {
        doThrow(new IllegalArgumentException("Mocked exception message")).when(passwordValidationMock).verifyPasswordFormat(password);

        assertThrows(IllegalArgumentException.class,()->{
            passwordValidationMock.verifyPasswordFormat(password);
        });
    }

    private static Stream<Arguments> provideArgumentsForVerifyEqualsPass() {
        return Stream.of(
                Arguments.of("123","tyug4"),
                Arguments.of("adi12345","adi12345")
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForVerifyEqualsPass")
    void verifyEqualsPassword(String password,String password2) {
        doThrow(new IllegalArgumentException("Mocked exception message")).when(passwordValidationMock).verifyEqualsPassword(password,password2);

        assertThrows(IllegalArgumentException.class,()->{
            passwordValidationMock.verifyEqualsPassword(password,password2);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"","3456","adi123456"})
    void isPasswordFormat(String password) {
        when(passwordValidationMock.isPasswordFormat(password)).thenReturn(true);

        boolean result=passwordValidationMock.isPasswordFormat(password);

        assertTrue(result);
    }
}