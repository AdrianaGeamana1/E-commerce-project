package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BirthdayValidationIntegrationTest {
    BirthdayValidation birthdayValidationTest;
    @BeforeEach
    public void setUp(){
        birthdayValidationTest=new BirthdayValidation();
    }

    @Test
    void isBirthdayFormat() {
        assertThrows(DateTimeException.class, () -> {
            birthdayValidationTest.isBirthdayFormat( LocalDate.of(23, 15, 7));
        });
    }

    @Test
    void calculateAge() {
        LocalDate birthDate=LocalDate.of(1990,6,7);

        int result= birthdayValidationTest.calculateAge(birthDate);

        assertEquals(34,result);
    }

    @Test
    void verifyValidAge_WhenLess15() {
        LocalDate birthDate=LocalDate.of(2019,6,7);

        assertThrows(ValidationErrorException.class,()->{
            birthdayValidationTest.verifyValidAge(birthDate);
        });

    }
    @Test
    void verifyValidAge_WhenGreater110() {
        LocalDate birthDate=LocalDate.of(1000,6,7);

        assertThrows(ValidationErrorException.class,()->{
            birthdayValidationTest.verifyValidAge(birthDate);
        });

    }
}