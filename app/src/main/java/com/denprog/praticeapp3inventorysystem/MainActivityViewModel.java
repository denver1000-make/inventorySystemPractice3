package com.denprog.praticeapp3inventorysystem;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.denprog.praticeapp3inventorysystem.callback.SimpleLoadingCallback;
import com.denprog.praticeapp3inventorysystem.room.AppDao;
import com.denprog.praticeapp3inventorysystem.room.entities.User;
import com.denprog.praticeapp3inventorysystem.room.views.UserWithProfile;
import com.denprog.praticeapp3inventorysystem.util.AsyncRunner;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {
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

}
