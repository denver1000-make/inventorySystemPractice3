package com.denprog.praticeapp3inventorysystem.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.denprog.praticeapp3inventorysystem.room.entities.InventoryItem;
import com.denprog.praticeapp3inventorysystem.room.entities.User;
import com.denprog.praticeapp3inventorysystem.room.entities.UserProfile;
import com.denprog.praticeapp3inventorysystem.room.views.UserWithProfile;

@Database(version = 2, entities = {User.class, UserProfile.class, InventoryItem.class}, views = {UserWithProfile.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao getAppDao();
}
