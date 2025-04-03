package com.denprog.praticeapp3inventorysystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.praticeapp3inventorysystem.callback.SimpleLoadingCallback;
import com.denprog.praticeapp3inventorysystem.room.AppDao;
import com.denprog.praticeapp3inventorysystem.room.entities.User;
import com.denprog.praticeapp3inventorysystem.room.views.UserWithProfile;
import com.denprog.praticeapp3inventorysystem.util.AsyncRunner;
import com.denprog.praticeapp3inventorysystem.util.FileUtil;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {
    public MutableLiveData<Long> userId = new MutableLiveData<>(null);
    AppDao appDao;
    @Inject
    public MainActivityViewModel(AppDao appDao) {
        this.appDao = appDao;
    }

    public void fetchUserProfileAndInfoByUserID(long userId, SimpleLoadingCallback<UserWithProfile> simpleLoadingCallback) {
        AsyncRunner.runAsync(new AsyncRunner.AsyncRunnerCallback<UserWithProfile>() {
            @Override
            public void onPreExecute() {
                simpleLoadingCallback.onLoading();
            }

            @Override
            public UserWithProfile onExecute() throws Exception {
                List<UserWithProfile> userWithProfiles = appDao.getUserWithProfileByUserId(userId);
                if (userWithProfiles.isEmpty()) {
                    throw new Exception("User does not exist");
                }
                return userWithProfiles.get(0);
            }

            @Override
            public void onFinish(UserWithProfile data) {

            }

            @Override
            public void onUI(UserWithProfile data) {
                simpleLoadingCallback.onFinished(data);
            }

            @Override
            public void onError(String message) {
                simpleLoadingCallback.onError(message);
            }
        });
    }

    public void loadBitmapFromInternalStorage(String path, Context context, SimpleLoadingCallback<Bitmap> simpleLoadingCallback) {
        AsyncRunner.runAsync(new AsyncRunner.AsyncRunnerCallback<Bitmap>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public Bitmap onExecute() throws Exception {
                return FileUtil.getBitmapFromPath(path, context);
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
                simpleLoadingCallback.onError(message);
            }
        });
    }

}
