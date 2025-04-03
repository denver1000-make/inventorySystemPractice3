package com.denprog.praticeapp3inventorysystem.callback;


public interface SimpleLoadingCallback<T> {
    void onFinished(T data);
    void onLoading();
    void onError(String message);
}
