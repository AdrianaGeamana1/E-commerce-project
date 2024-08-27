package siit.validation;

import java.time.DateTimeException;
import java.time.LocalDate;

public class BirthdayValidation {
    public void isBirthdayFormat(LocalDate birthday){
        try{
            LocalDate localDate=LocalDate.of(birthday.getYear(), birthday.getMonth(),birthday.getDayOfMonth());
        }catch (DateTimeException e){
            throw new DateTimeException("Invalid date format");
        }
    }
    public int calculateAge(LocalDate birthday){
        int yearOfBirth=birthday.getYear();
        return LocalDate.now().getYear()-yearOfBirth;

    }
    public void verifyValidAge(LocalDate birthday){
        int age=calculateAge(birthday);
        if(age<15||age>110){
            throw new ValidationErrorException("Your age must be between 15 and 110");
        }
    }
}
