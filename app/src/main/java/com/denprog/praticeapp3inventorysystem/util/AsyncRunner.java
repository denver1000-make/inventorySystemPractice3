package com.denprog.praticeapp3inventorysystem.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncRunner {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Handler handler = new Handler(Looper.getMainLooper());


    public static <T> void runAsync(AsyncRunnerCallback<T> asyncRunnerCallback) {
        asyncRunnerCallback.onPreExecute();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    T data = asyncRunnerCallback.onExecute();
                    asyncRunnerCallback.onFinish(data);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            asyncRunnerCallback.onUI(data);
                        }
                    });
                } catch (Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            asyncRunnerCallback.onError(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    public interface AsyncRunnerCallback<T> {
        void onPreExecute();
        T onExecute() throws Exception;
        void onFinish(T data);
        void onUI(T data);
        void onError(String message);
    }

}
