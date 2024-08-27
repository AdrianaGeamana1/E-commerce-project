package siit.validation;

public class ValidationErrorException extends RuntimeException{
    public ValidationErrorException(){super("Invalid input");}
    public ValidationErrorException(String message){super(message);}
}
