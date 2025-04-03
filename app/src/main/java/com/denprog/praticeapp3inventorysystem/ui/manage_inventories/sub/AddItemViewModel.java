package com.denprog.praticeapp3inventorysystem.ui.manage_inventories.sub;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.praticeapp3inventorysystem.callback.SimpleLoadingCallback;
import com.denprog.praticeapp3inventorysystem.room.AppDao;
import com.denprog.praticeapp3inventorysystem.room.entities.InventoryItem;
import com.denprog.praticeapp3inventorysystem.room.entities.InventoryItemImage;
import com.denprog.praticeapp3inventorysystem.util.AsyncRunner;
import com.denprog.praticeapp3inventorysystem.util.FileUtil;
import com.denprog.praticeapp3inventorysystem.util.Validator;

import org.jetbrains.annotations.Async;

import java.io.File;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddItemViewModel extends ViewModel {
    public MutableLiveData<AddItemFragmentState> mutableLiveData = new MediatorLiveData<>(null);
    public MutableLiveData<Bitmap> itemImageMutableLiveData = new MutableLiveData<>(null);
    public MutableLiveData<AddItemFormState> addItemFormStateMutableLiveData = new MutableLiveData<>(new AddItemFormState(null, null, null, false));
    public MutableLiveData<View.OnClickListener> onClickListenerMutableLiveData = new MutableLiveData<>(null);
    public MutableLiveData<Bitmap> getItemImageMutableLiveData = new MutableLiveData<>();
    AppDao appDao;

    @Inject
    public AddItemViewModel(AppDao appDao) {
        this.appDao = appDao;
    }

    public void onDataChange(String itemName, String itemPrice, String itemStockAmount) {
        AddItemFormState itemFormState = new AddItemFormState(null, null, null, false);
        itemFormState.itemNameError = Validator.validateItemName(itemName);
        itemFormState.itemPriceError = Validator.validateItemPrice(itemPrice);
        itemFormState.itemStockAmountError = Validator.validateItemStock(itemStockAmount);
        itemFormState.isDataValid = itemFormState.itemPriceError == null && itemFormState.itemNameError == null && itemFormState.itemStockAmountError == null;
        addItemFormStateMutableLiveData.setValue(itemFormState);
    }

    public void insertItem(Context context, String itemName, String itemPrice, String itemStockAmount, long userId, Bitmap bitmap, SimpleLoadingCallback<InventoryItem> simpleLoadingCallback) {
        AsyncRunner.runAsync(new AsyncRunner.AsyncRunnerCallback<InventoryItem>() {
            @Override
            public void onPreExecute() {
                simpleLoadingCallback.onLoading();
            }

            @Override
            public InventoryItem onExecute() throws Exception {
                InventoryItem inventoryItem = new InventoryItem(itemName, Float.parseFloat(itemPrice), Integer.parseInt(itemStockAmount), userId);
                long inventoryItemId = appDao.insertInventoryItem(inventoryItem);
                String imagePath = FileUtil.insertProfilePicture(bitmap, context, FileUtil.inventoryImagesPath,  inventoryItemId + FileUtil.inventoryItemImageAppend, FileUtil.generateRandomStr(6) + ".jpeg");
                long itemImageId = appDao.insertInventoryItemImage(new InventoryItemImage(imagePath, inventoryItemId));
                return inventoryItem;
            }

            @Override
            public void onFinish(InventoryItem data) {

            }

            @Override
            public void onUI(InventoryItem data) {
                simpleLoadingCallback.onFinished(data);
            }

            @Override
            public void onError(String message) {
                simpleLoadingCallback.onError(message);
            }
        });
    }

    public void convertUriToBitmap(Uri uri, Context context, SimpleLoadingCallback<Bitmap> simpleLoadingCallback) {
        AsyncRunner.runAsync(new AsyncRunner.AsyncRunnerCallback<Bitmap>() {
            @Override
            public void onPreExecute() {
                simpleLoadingCallback.onLoading();
            }

            @Override
            public Bitmap onExecute() throws Exception {
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
                simpleLoadingCallback.onError(message);
            }
        });
    }
}