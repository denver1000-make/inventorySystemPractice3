package com.denprog.praticeapp3inventorysystem.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserProfile {
    @PrimaryKey(autoGenerate = true)
    public long userProfileId;
    public String userProfilePath;
    public long userId;

    public UserProfile(String userProfilePath, long userId) {
        this.userProfilePath = userProfilePath;
        this.userId = userId;
    }
}
