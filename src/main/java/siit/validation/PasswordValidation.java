package siit.validation;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation {
    public void passwordIsEmptyOrNull(String password){
        if(password.isEmpty()|| Objects.equals(password,null)){
            throw new ValidationErrorException("password can not be empty or null");
        }
    }
    public void password2IsEmptyOrNull(String password2){
        if(password2.isEmpty()|| Objects.equals(password2,null)){
            throw new ValidationErrorException("confirmed password can not be empty or null");
        }
    }
    public void verifyPasswordFormat(String password){
        if(!isPasswordFormat(password)){
            throw new ValidationErrorException("Invalid password format");
        }
    }
    public void verifyEqualsPassword(String password,String password2){
        if(!password2.equals(password)){
            throw new ValidationErrorException("Password and confirmed password must be equals");
        }
    }
    public boolean isPasswordFormat(String password){
        String passwordRegex="^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$"; // Requires at least one letter and one digit, minimum length of 6 characters
        Pattern pattern= Pattern.compile(passwordRegex);
        Matcher matcher=pattern.matcher(password);
        return matcher.matches();
    }
}

