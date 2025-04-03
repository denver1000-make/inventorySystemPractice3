package com.denprog.praticeapp3inventorysystem.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.praticeapp3inventorysystem.callback.SimpleLoadingCallback;
import com.denprog.praticeapp3inventorysystem.room.AppDao;
import com.denprog.praticeapp3inventorysystem.room.entities.User;
import com.denprog.praticeapp3inventorysystem.util.AsyncRunner;
import com.denprog.praticeapp3inventorysystem.util.Validator;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    public MutableLiveData<LoginFormState> loginFormStateMutableLiveData = new MutableLiveData<>(new LoginFormState(null, null, false));
    private AppDao appDao;

    @Inject
    public LoginViewModel(AppDao appDao) {
        this.appDao = appDao;
    }

    public void onDataChanged(String email, String password) {
        LoginFormState loginFormState = new LoginFormState(null, null, null);
        String emailError = Validator.validateEmail(email);
        String passwordError = Validator.validateName(password);

        loginFormState.isDataValid = emailError == null && passwordError == null;
        loginFormStateMutableLiveData.setValue(loginFormState);
    }

    public void login(String email, String password, SimpleLoadingCallback<User> simpleLoadingCallback) {
        AsyncRunner.runAsync(new AsyncRunner.AsyncRunnerCallback<User>() {
            @Override
            public void onPreExecute() {
                simpleLoadingCallback.onLoading();
            }

            @Override
            public User onExecute() throws Exception {
                List<User> users = appDao.getUsersByEmailAndPassword(email, password);
                if (users.isEmpty()) {
                    throw new Exception("User not found");
                }
                return users.get(0);
            }

            @Override
            public void onFinish(User data) {

            }

            @Override
            public void onUI(User data) {
                simpleLoadingCallback.onFinished(data);
            }

            @Override
            public void onError(String message) {
                simpleLoadingCallback.onError(message);
            }
        });
    }
}