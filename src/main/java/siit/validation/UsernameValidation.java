package siit.validation;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidation {
    public void usernameIsEmptyOrNull(String username){
        if(username.isEmpty()|| Objects.equals(username,null)){
            throw new ValidationErrorException("username can not be empty or null");
        }
    }
    public void verifyUsernameFormat(String username){
        if(!isUsernameFormat(username)){
            throw new ValidationErrorException("Invalid username format");
        }
    }
    public boolean isUsernameFormat(String username){
        String usernameRegex= "^[a-zA-Z0-9_-]{3,16}$"; // Example pattern: alphanumeric, underscores, and hyphens, 3 to 16 characters long
        Pattern pattern= Pattern.compile(usernameRegex);
        Matcher matcher=pattern.matcher(username);
        return matcher.matches();
    }
}
