package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class EmailValidationIntegrationTest {
    EmailValidation emailValidationTest;
    @BeforeEach
    public void setUp(){
        emailValidationTest=new EmailValidation();
    }

    @Test
    void emailIsEmptyOrNull() {
        String email="";

        assertThrows(ValidationErrorException.class,()->{
            emailValidationTest.emailIsEmptyOrNull(email);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"adriana.io","adi.c&.com","adi.c@...is,j"})
    void verifyEmailFormat(String email) {
        assertThrows(ValidationErrorException.class,()->{
            emailValidationTest.verifyEmailFormat(email);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"adriana.io","adi.c&.com","adi.c@...is,j"})
    void isEmailFormat(String email) {
        boolean result=emailValidationTest.isEmailFormat(email);

        assertFalse(result);
    }
}