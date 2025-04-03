package com.denprog.praticeapp3inventorysystem.ui.manage_inventories;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.praticeapp3inventorysystem.room.AppDao;
import com.denprog.praticeapp3inventorysystem.room.entities.InventoryItem;
import com.denprog.praticeapp3inventorysystem.room.views.InventoryItemWithImage;
import com.denprog.praticeapp3inventorysystem.util.AsyncRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class InventoryManagementViewModel extends ViewModel {

    public MutableLiveData<List<InventoryItemWithImage>> listMutableLiveData = new MediatorLiveData<>(new ArrayList<>());
    AppDao appDao;
    @Inject
    public InventoryManagementViewModel(AppDao appDao) {
        this.appDao = appDao;
    }

    public void fetchInventoryItems(long userId) {
        AsyncRunner.runAsync(new AsyncRunner.AsyncRunnerCallback<List<InventoryItemWithImage>>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public List<InventoryItemWithImage> onExecute() throws Exception {
                return appDao.getAllInventoryItemWithImageByUserId(userId);
            }

            @Override
            public void onFinish(List<InventoryItemWithImage> data) {

            }

            @Override
            public void onUI(List<InventoryItemWithImage> data) {
                listMutableLiveData.setValue(data);
            }

            @Override
            public void onError(String message) {

            }
        });
    }


}
