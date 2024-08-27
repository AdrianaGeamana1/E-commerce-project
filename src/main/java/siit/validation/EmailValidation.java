package siit.validation;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
    public void emailIsEmptyOrNull(String email){
        if(email.isEmpty()|| Objects.equals(email,null)){
            throw new ValidationErrorException("email can not be empty or null");
        }
    }
    public void verifyEmailFormat(String email){
        if(!isEmailFormat(email)){
            throw new ValidationErrorException("Invalid email format");
        }
    }
    public boolean isEmailFormat(String email){
        String emailRegex= "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern= Pattern.compile(emailRegex);
        Matcher matcher=pattern.matcher(email);
        return matcher.matches();

    }
}
