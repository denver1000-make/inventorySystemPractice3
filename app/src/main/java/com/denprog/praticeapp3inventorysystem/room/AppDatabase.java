package com.denprog.praticeapp3inventorysystem.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.denprog.praticeapp3inventorysystem.room.entities.User;
import com.denprog.praticeapp3inventorysystem.room.entities.UserProfile;

@Database(version = 1, entities = {User.class, UserProfile.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao getAppDao();
}
