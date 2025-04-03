package com.denprog.praticeapp3inventorysystem.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Nullable;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public long userId;
    public String username;
    public String firstName;
    @Nullable
    public String middleName = null;
    public String lastName;
    public String email;
    public String password;

    public User(String username, String firstName, @Nullable String middleName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}

