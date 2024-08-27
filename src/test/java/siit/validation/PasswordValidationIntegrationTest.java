package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidationIntegrationTest {
    PasswordValidation passwordValidationTest;
    @BeforeEach
    public void setUp(){
        passwordValidationTest=new PasswordValidation();
    }

    @Test
    void passwordIsEmptyOrNull() {
        String password="";
        assertThrows(ValidationErrorException.class,()->{
            passwordValidationTest.passwordIsEmptyOrNull(password);
        });
    }

    @Test
    void password2IsEmptyOrNull() {
        String password2="";
        assertThrows(ValidationErrorException.class,()->{
            passwordValidationTest.password2IsEmptyOrNull(password2);
        });
    }

    // Requires at least one letter and one digit, minimum length of 6 characters
    @ParameterizedTest
    @ValueSource(strings = {"t1","3456","adrian"})
    void verifyPasswordFormat(String password) {
        assertThrows(ValidationErrorException.class,()->{
            passwordValidationTest.verifyPasswordFormat(password);
        });
    }

    @Test
    void verifyEqualsPassword() {
        String password="adi1234";
        String password2="adi7899999";

        assertThrows(ValidationErrorException.class,()->{
            passwordValidationTest.verifyEqualsPassword(password,password2);
        });
    }

    // Requires at least one letter and one digit, minimum length of 6 characters
    @ParameterizedTest
    @ValueSource(strings = {"t1","3456","adrian"})
    void isPasswordFormat_WhenFalse(String password) {
        boolean result=passwordValidationTest.isPasswordFormat(password);

        assertFalse(result);
    }
    @ParameterizedTest
    @ValueSource(strings = {"adi12345","adrian5","adrianBodea12"})
    void isPasswordFormat_WhenTrue(String password) {
        boolean result=passwordValidationTest.isPasswordFormat(password);

        assertTrue(result);
    }
}