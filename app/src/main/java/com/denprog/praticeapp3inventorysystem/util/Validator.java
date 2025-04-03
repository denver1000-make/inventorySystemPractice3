package com.denprog.praticeapp3inventorysystem.util;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static Pattern passwordContentValidator = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*()]).{8,}$");

    public static boolean isPasswordContentAndLengthValid(String password) {
        Matcher matcher = passwordContentValidator.matcher(password);
        return matcher.matches();
    }

    public static  boolean isInputNullOrEmpty(String input) {
        return input == null || input.isEmpty();
    }

    public static  boolean isEmailValid(String email) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        return matcher.matches();
    }

    public static  String validateEmail(String email) {
        if (isInputNullOrEmpty(email)) {
            return "Empty Field";
        } else if (!isEmailValid(email)) {
            return "Invalid Email Format";
        }
        return null;
    }

    public static String validateName(String name) {
        if (isInputNullOrEmpty(name)) {
            return "Empty Field";
        }
        return null;
    }

    public static String confirmPasswordError(String password, String confirmPassword) {
            if (!password.equals(confirmPassword)) {
            return "Password does not match";
        }
        return null;
    }

    public static String validatePassword(String password) {
        if (isInputNullOrEmpty(password)) {
            return "Empty Field";
        } else if (!isPasswordContentAndLengthValid(password)) {
            return "Password must be at least 8 characters long and must include at least 1 uppercase, 1 lower case, and must include special character (!@#$%^&*).";
        }
        return null;
    }

}
