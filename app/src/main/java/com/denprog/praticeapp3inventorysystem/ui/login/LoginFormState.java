package com.denprog.praticeapp3inventorysystem.ui.login;

public class LoginFormState {

    public String emailError = null;
    public String passwordError = null;
    public Boolean isDataValid = false;

    public LoginFormState(String emailError, String passwordError, Boolean isDataValid) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.isDataValid = isDataValid;
    }

}
