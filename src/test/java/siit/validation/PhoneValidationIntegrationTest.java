package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PhoneValidationIntegrationTest {
    PhoneValidation phoneValidationTest;
    @BeforeEach
    public void setUp(){
        phoneValidationTest=new PhoneValidation();
    }
    @Test
    void phoneIsEmptyOrNull() {
        String phone="";
        assertThrows(ValidationErrorException.class,()->{
            phoneValidationTest.phoneIsEmptyOrNull(phone);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"06",".56.89.78.7(879)","123567"})
    void verifyPhoneFormat(String phone) {

        assertThrows(ValidationErrorException.class,()->{
            phoneValidationTest.verifyPhoneFormat(phone);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"2055550125","202 555 0125", "(202) 555-0125", "+111 (202) 555-0125",
            "636 856 789", "+111 636 856 789", "636 85 67 89", "+111 636 85 67 89"})
    void isPhoneFormat(String phone) {
        boolean result=phoneValidationTest.isPhoneFormat(phone);
    }
}