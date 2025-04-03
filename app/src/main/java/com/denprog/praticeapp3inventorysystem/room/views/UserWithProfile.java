package com.denprog.praticeapp3inventorysystem.room.views;

import androidx.room.DatabaseView;

@DatabaseView("SELECT " +
        "User.userId, " +
        "User.firstName, " +
        "User.middleName, " +
        "User.lastName, " +
        "User.email, " +
        "UserProfile.userProfilePath " +
        "FROM User " +
        "INNER JOIN " +
        "UserProfile " +
        "ON User.userId = UserProfile.userId")
public class UserWithProfile {
    public long userId;
    public String firstName;
    public String middleName;
    public String lastName;
    public String email;
    public String password;
    public String userProfilePath;
}
