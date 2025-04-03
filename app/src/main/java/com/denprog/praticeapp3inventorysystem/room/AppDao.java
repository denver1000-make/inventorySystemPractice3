package com.denprog.praticeapp3inventorysystem.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.denprog.praticeapp3inventorysystem.room.entities.InventoryItem;
import com.denprog.praticeapp3inventorysystem.room.entities.InventoryItemImage;
import com.denprog.praticeapp3inventorysystem.room.entities.User;
import com.denprog.praticeapp3inventorysystem.room.entities.UserProfile;
import com.denprog.praticeapp3inventorysystem.room.views.InventoryItemWithImage;
import com.denprog.praticeapp3inventorysystem.room.views.UserWithProfile;

import java.util.List;

@Dao
public interface AppDao {
    @Insert
    long insertUser(User user);
    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    List<User> getUsersByEmailAndPassword(String email, String password);
    @Insert
    long insertUserProfile(UserProfile userProfile);
    @Query("SELECT * FROM User WHERE email =:email")
    List<User> checkIfEmailExist(String email);
    @Query("SELECT * FROM UserWithProfile WHERE userId = :userId")
    List<UserWithProfile> getUserWithProfileByUserId(long userId);
    @Insert
    long insertInventoryItem(InventoryItem inventoryItem);

    @Insert
    long insertInventoryItemImage(InventoryItemImage inventoryItemImage);

    @Query("SELECT * FROM InventoryItemWithImage WHERE addedByUser = :userId")
    List<InventoryItemWithImage> getAllInventoryItemWithImageByUserId(long userId);
}
