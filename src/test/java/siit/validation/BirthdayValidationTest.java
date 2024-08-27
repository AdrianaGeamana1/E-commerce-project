package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BirthdayValidationTest {
    BirthdayValidation birthdayValidationMock;
    @BeforeEach
    public void setUp(){
        birthdayValidationMock=mock(BirthdayValidation.class);
    }

    @Test
    void isBirthdayFormat() {
        LocalDate birthDate=LocalDate.of(1970,12,9);
        doThrow(new IllegalArgumentException("Mocked exception message")).when(birthdayValidationMock).isBirthdayFormat(birthDate);

        assertThrows(IllegalArgumentException.class,()->{
            birthdayValidationMock.isBirthdayFormat(birthDate);
        });
    }

    @Test
    void calculateAge() {
        LocalDate birthDate=LocalDate.of(1999,12,9);

        when(birthdayValidationMock.calculateAge(birthDate)).thenReturn(33);

        int result=birthdayValidationMock.calculateAge(birthDate);

        assertEquals(33,result);
    }

    @Test
    void verifyValidAge() {
        LocalDate birthDate=LocalDate.of(1970,12,9);
        doThrow(new IllegalArgumentException("Mocked exception message")).when(birthdayValidationMock).verifyValidAge(birthDate);

        assertThrows(IllegalArgumentException.class,()->{
            birthdayValidationMock.verifyValidAge(birthDate);
        });
    }
}