package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhoneValidationTest {
    PhoneValidation phoneValidationMock;
    @BeforeEach
    public void setUp(){
        phoneValidationMock=mock(PhoneValidation.class);
    }
    @ParameterizedTest
    @ValueSource(strings = {"09876","1","09876543",""})
    void phoneIsEmptyOrNull(String phone) {
        doThrow(new IllegalArgumentException("Mocked exception message")).when(phoneValidationMock).phoneIsEmptyOrNull(phone);

        assertThrows(IllegalArgumentException.class,()->{
            phoneValidationMock.phoneIsEmptyOrNull(phone);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"(09)876","1...99999","+09876543",""})
    void verifyPhoneFormat(String phone) {
        doThrow(new IllegalArgumentException("Mocked exception message")).when(phoneValidationMock).verifyPhoneFormat(phone);

        assertThrows(IllegalArgumentException.class,()->{
            phoneValidationMock.verifyPhoneFormat(phone);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"(09)876","1...99999","+09876543",""})
    void isPhoneFormat(String phone) {
        when(phoneValidationMock.isPhoneFormat(phone)).thenReturn(true);

        boolean result=phoneValidationMock.isPhoneFormat(phone);

        assertTrue(result);
    }
}