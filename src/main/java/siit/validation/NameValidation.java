package siit.validation;

import java.util.Objects;

public class NameValidation {
    public void nameIsEmptyOrNull(String name){
        if(name.isEmpty()||Objects.equals(name,null)){
            throw new ValidationErrorException("name can't be empty or null");
        }
    }
    public void verifyNameFormat(String name){
        if(!isOnlyLetters(name)){
            throw new ValidationErrorException("Invalid name format");
        }
    }
    public void verifyNameLength(String name){
        if(name.length()<7||name.length()>25){
            throw new ValidationErrorException("Name must be between 7 and 25 characters long");
        }
    }
    public boolean isOnlyLetters(String name){
        return name.matches("^[a-zA-Z\\s]+$");
    }
}
