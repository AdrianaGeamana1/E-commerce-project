package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsernameValidationIntegrationTest {
     UsernameValidation usernameValidationTest;
     @BeforeEach
     public void setUp(){
         usernameValidationTest=new UsernameValidation();
     }
    @Test
    void usernameIsEmptyOrNull() {
         String username="";
         assertThrows(ValidationErrorException.class,()->{
             usernameValidationTest.usernameIsEmptyOrNull(username);
         });
    }

    @Test
    void verifyUsernameFormat() {
        String username="adi$$$$$$";
        assertThrows(ValidationErrorException.class,()->{
            usernameValidationTest.verifyUsernameFormat(username);
        });
    }

    @Test
    void isUsernameFormat_WhenTrue() {
        String username="adi_999";

        boolean result=usernameValidationTest.isUsernameFormat(username);

        assertTrue(result);
    }
    @Test
    void isUsernameFormat_WhenFalse() {
        String username="adi_#$$$";

        boolean result=usernameValidationTest.isUsernameFormat(username);

        assertFalse(result);
    }
}