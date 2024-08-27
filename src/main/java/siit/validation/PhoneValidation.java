package siit.validation;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidation {
    public void phoneIsEmptyOrNull(String phone) {
        if (phone.isEmpty() || Objects.equals(phone, null)) {
            throw new ValidationErrorException("phone can not be empty or null");
        }
    }
    public void verifyPhoneFormat(String phone){
        if(!isPhoneFormat(phone)){
            throw new ValidationErrorException("Invalid phone format");
        }
    }

    public boolean isPhoneFormat(String phone) {
        String phoneRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
