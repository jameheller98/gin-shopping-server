package com.gin.usermicroservice.validate;

import com.gin.usermicroservice.resource.dto.RegisterRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserHelper {
    public static String registerValidate(RegisterRequest registerRequest) {
        if (registerRequest.getEmail() == null ||
                registerRequest.getPassword() == null ||
                registerRequest.getFirstName() == null ||
                registerRequest.getLastName() == null
        ) return "All field is required.";

        Pattern patternEmail = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcherEmail = patternEmail.matcher(registerRequest.getEmail());
        Pattern patternPassLength = Pattern.compile(".{8,}");
        Matcher matcherPassLength = patternPassLength.matcher(registerRequest.getPassword());
        Pattern patternPassOneLowercase = Pattern.compile("[a-z]+");
        Matcher matcherPassOneLowercase = patternPassOneLowercase.matcher(registerRequest.getPassword());
        Pattern patternPassOneUppercase = Pattern.compile("[A-Z]+");
        Matcher matcherPassOneUppercase = patternPassOneUppercase.matcher(registerRequest.getPassword());
        Pattern patternPassOneDigit = Pattern.compile("[0-9]+");
        Matcher matcherPassOneDigit = patternPassOneDigit.matcher(registerRequest.getPassword());
        Pattern patternPassOneSpecialChar = Pattern.compile("[!@#$&*]+");
        Matcher matcherPassOneSpecialChar = patternPassOneSpecialChar.matcher(registerRequest.getPassword());


        if (registerRequest.getEmail().length() < 5) return "Email has must least 5 characters.";

        if (!matcherEmail.matches()) return "Email invalid.";

        if (!matcherPassOneLowercase.find()) return "Password has must least 1 lowercase letter.";

        if (!matcherPassOneUppercase.find()) return "Password has must least 1 uppercase letter.";

        if (!matcherPassOneDigit.find()) return "Password has must least 1 digit.";

        if (!matcherPassOneSpecialChar.find()) return "Password has must least 1 special character.";

        if (!matcherPassLength.matches()) return "Password has must least 8 characters.";

        return "";
    }
}
