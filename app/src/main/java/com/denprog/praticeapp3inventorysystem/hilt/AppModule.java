package com.denprog.praticeapp3inventorysystem.hilt;

import android.content.Context;

import androidx.room.Room;

import com.denprog.praticeapp3inventorysystem.room.AppDao;
import com.denprog.praticeapp3inventorysystem.room.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public static AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "AppDatabase").fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    public static AppDao provideAppDao(AppDatabase appDatabase) {
        return appDatabase.getAppDao();
    }
}
