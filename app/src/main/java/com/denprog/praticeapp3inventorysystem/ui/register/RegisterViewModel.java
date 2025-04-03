package com.denprog.praticeapp3inventorysystem.ui.register;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.praticeapp3inventorysystem.callback.SimpleLoadingCallback;
import com.denprog.praticeapp3inventorysystem.room.AppDao;
import com.denprog.praticeapp3inventorysystem.room.entities.User;
import com.denprog.praticeapp3inventorysystem.room.entities.UserProfile;
import com.denprog.praticeapp3inventorysystem.util.AsyncRunner;
import com.denprog.praticeapp3inventorysystem.util.FileUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {
    public MutableLiveData<Bitmap> bitmapMutableLiveData = new MutableLiveData<>(null);
    public ObservableField<String> firstNameField = new ObservableField<>("");
    public ObservableField<String> middleNameField = new ObservableField<>("");
    public ObservableField<String> lastNameField = new ObservableField<>("");
    public ObservableField<String> userNameField = new ObservableField<>("");
    public ObservableField<String> emailField = new ObservableField<>("");
    public ObservableField<String> passwordField = new ObservableField<>("");
    public ObservableField<String> confirmPasswordField = new ObservableField<>("");
    AppDao appDao;

    @Inject
    public RegisterViewModel(AppDao appDao) {
        this.appDao = appDao;
    }

    public void register(Context context, Bitmap bitmap,  String firstName, String middleName, String lastName, String username, String email, String password, SimpleLoadingCallback<User> simpleLoadingCallback) {
        AsyncRunner.runAsync(new AsyncRunner.AsyncRunnerCallback<User>() {
            @Override
            public void onPreExecute() {
                simpleLoadingCallback.onLoading();
            }

            @Override
            public User onExecute() throws Exception {
                List<User> usersWithSameEmail = appDao.checkIfEmailExist(email);
                if (!usersWithSameEmail.isEmpty()) {
                    throw new Exception("Email Already Exist.");
                }
                User user = new User(username, firstName, middleName, lastName, email, password);
                long userId = appDao.insertUser(user);
                String profilePath = FileUtil.insertProfilePicture(bitmap, context, FileUtil.profileImagesPath, userId + FileUtil.userProfileFolderAppend, FileUtil.generateRandomStr(5) + ".jpeg");
                long profileId = appDao.insertUserProfile(new UserProfile(profilePath,userId));
                return user;
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

    public void convertUriToBitmap (Uri uri, Context context, SimpleLoadingCallback<Bitmap> simpleLoadingCallback) {
        AsyncRunner.runAsync(new AsyncRunner.AsyncRunnerCallback<Bitmap>() {
            @Override
            public void onPreExecute() {
                simpleLoadingCallback.onLoading();
            }

            @Override
            public Bitmap onExecute() throws FileNotFoundException {
                return FileUtil.convertUriToBitmap(uri, context);
            }

            @Override
            public void onFinish(Bitmap data) {
            }

            @Override
            public void onUI(Bitmap data) {
                simpleLoadingCallback.onFinished(data);
            }

            @Override
            public void onError(String message) {
                bitmapMutableLiveData.setValue(null);
                simpleLoadingCallback.onError(message);
            }
        });
    }



}
