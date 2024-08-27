package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameValidationIntegrationTest {
    NameValidation nameValidationTest;
    @BeforeEach
    public void setUp(){
        nameValidationTest=new NameValidation();
    }
    @Test
    void nameIsEmptyOrNull() {
        String name="";

        assertThrows(ValidationErrorException.class,()->{
            nameValidationTest.nameIsEmptyOrNull(name);
        });
    }

    @Test
    void verifyNameFormat() {
        String name="a$$$76aaa";
        assertThrows(ValidationErrorException.class,()->{
            nameValidationTest.verifyNameFormat(name);
        });
    }

    @Test
    void verifyNameLength_WhenLess7() {
        String name="adi";
        assertThrows(ValidationErrorException.class,()->{
            nameValidationTest.verifyNameLength(name);
        });
    }
    @Test
    void verifyNameLength_WhenGreater25() {
        String name="adrianAlexBodeaGeorgescuIlieMarin";
        assertThrows(ValidationErrorException.class,()->{
            nameValidationTest.verifyNameLength(name);
        });
    }

    @Test
    void isOnlyLetters_True() {
        String name="alexVid marin";

        boolean result=nameValidationTest.isOnlyLetters(name);

        assertTrue(result);
    }
    @Test
    void isOnlyLetters_False() {
        String name="alexVid !$%^1";

        boolean result=nameValidationTest.isOnlyLetters(name);

        assertFalse(result);
    }
}